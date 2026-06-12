package com.sample.golfperformancetracker

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.sample.data.sync.ConnectivitySyncTrigger
import com.sample.data.sync.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class GolfPerformanceTrackerApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var connectivitySync: ConnectivitySyncTrigger

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        // Watch for connectivity changes and trigger sync automatically.
        connectivitySync.start()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Schedule a periodic background sync (every 6 hours) so the cache
        // stays fresh even when the user isn't actively using the app.
        SyncWorker.schedulePeriodicSync(workManager)
    }

    override fun getWorkManagerConfiguration(): Configuration = Configuration.Builder()
        .setWorkerFactory(workerFactory)
        .build()
}