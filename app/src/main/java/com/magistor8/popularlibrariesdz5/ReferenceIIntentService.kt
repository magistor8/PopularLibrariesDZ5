package com.magistor8.popularlibrariesdz5

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast

private const val MESSAGE = "MESSAGE_KEY"
private const val DELAY = "DELAY_KEY"

class ReferenceIIntentService: IntentService("ReferenceIIntentService") {

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            showToastWithDelay(it)
        }
    }

    private fun showToastWithDelay(intent: Intent) {
        val message: String = intent.getStringExtra(MESSAGE).toString()
        val delay: Long = intent.getLongExtra(DELAY, 0)

        Thread.sleep(delay)

        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun startToastService(context: Context, message: String, delay: Long) {
            val intent = Intent(context, ReferenceIIntentService::class.java)
            intent.putExtra(MESSAGE, message)
            intent.putExtra(DELAY, delay)
            //Старт сервиса
            context.startService(intent)
        }
    }

}