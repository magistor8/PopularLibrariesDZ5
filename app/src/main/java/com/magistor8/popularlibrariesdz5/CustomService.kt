package com.magistor8.popularlibrariesdz5

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.annotation.WorkerThread

abstract class CustomService : Service() {

    private lateinit var workerThread: CustomWorkerThread

    override fun onCreate() {
        super.onCreate()

        workerThread = CustomWorkerThread()
        workerThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        workerThread.quit()
    }

    @WorkerThread
    protected abstract fun onHandleIntent(intent: Intent?)

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        workerThread.post(
            { onHandleIntent(intent)
                stopSelf(startId) },
            CustomWorkerThread.Priority.NORMAL
        )

        return START_REDELIVER_INTENT
    }
}