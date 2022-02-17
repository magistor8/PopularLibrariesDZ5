package com.magistor8.popularlibrariesdz5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.magistor8.popularlibrariesdz5.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val workerThread = CustomWorkerThread()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            workerThread.start()
        }
        binding.runButtonHigh.setOnClickListener {
            workerThread.post({Thread.sleep(1_000)}, CustomWorkerThread.Priority.HIGH)
        }
        binding.runButtonNormal.setOnClickListener {
            workerThread.post({Thread.sleep(1_000)}, CustomWorkerThread.Priority.NORMAL)
        }
        binding.runButtonLow.setOnClickListener {
            workerThread.post({Thread.sleep(1_000)}, CustomWorkerThread.Priority.LOW)
            workerThread.post({Thread.sleep(1_000)}, CustomWorkerThread.Priority.LOW)
            workerThread.post({Thread.sleep(1_000)}, CustomWorkerThread.Priority.LOW)
        }

        binding.stopButton.setOnClickListener {
            workerThread.quit()
        }

        binding.nextButton.setOnClickListener {
            CustomIntentService.startToastJob(this, UUID.randomUUID().toString(), 3_000L)
        }
    }




}