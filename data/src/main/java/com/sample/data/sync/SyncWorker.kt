package com.sample.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import repository.OfflineFirstGolfRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit

private const val TAG = "SyncWorker"

/**
 * WorkManager worker that runs whenever the device (re-)gains connectivity.
 *
 * Responsibilities:
 *  - Push all locally-pending articles to the server ([ArticleRepository.syncPending]).
 *  - Fetch the latest articles from the server to keep the cache fresh.
 *
 * Scheduling strategy:
 *  - Constrained to require [NetworkType.CONNECTED] so the OS queues it when
 *    offline and executes it automatically on reconnection.
 *  - Uses [ExistingWorkPolicy.KEEP] so duplicate schedules are ignored.
 */
@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: OfflineFirstGolfRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Timber.d("Triggering player sync")

        return try {
            repository.syncPlayers()

            Timber.d("Sync complete")
            Result.success()
        } catch (e: Exception) {
            Timber.e(
                e,
                "Sync failed"
            )

            if (runAttemptCount < MAX_RETRIES) Result.retry() else Result.failure()
        }
    }

    companion object {
        private const val MAX_RETRIES = 3
        const val WORK_NAME = "SyncWorker"

        /**
         * Enqueues a one-time sync request that will run as soon as the device
         * has network connectivity.
         */
        fun enqueue(workManager: WorkManager) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = OneTimeWorkRequestBuilder<SyncWorker>()
                .setConstraints(constraints)
                .setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.EXPONENTIAL,
                    backoffDelay = 15,
                    timeUnit = TimeUnit.SECONDS
                )
                .build()

            workManager.enqueueUniqueWork(
                WORK_NAME,
                ExistingWorkPolicy.KEEP,
                request
            )
        }

        /**
         * Schedules a periodic sync every 6 hours so the cache
         * stays fresh even when the user hasn't opened the app.
         */
        fun schedulePeriodicSync(workManager: WorkManager, intervalHours: Long = 6) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val request = PeriodicWorkRequestBuilder<SyncWorker>(
                repeatInterval = intervalHours,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "${WORK_NAME}_periodic",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
