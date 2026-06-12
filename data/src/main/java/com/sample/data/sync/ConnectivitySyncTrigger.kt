package com.sample.data.sync

import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Watches [NetworkMonitor] and enqueues a [SyncWorker] whenever the device
 * transitions from **offline → online**.
 *
 * The first emission is dropped because it just represents the initial state,
 * not a connectivity change.
 *
 * Call [start] once from your Application class (or a Hilt initializer).
 */
@Singleton
class ConnectivitySyncTrigger @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val workManager: WorkManager
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    fun start() {
        scope.launch {
            networkMonitor.isOnline
                .distinctUntilChanged()
                .drop(1)               // ignore the initial / boot state
                .filter { isOnline -> isOnline }
                .collect {
                    SyncWorker.Companion.enqueue(workManager)
                }
        }
    }
}
