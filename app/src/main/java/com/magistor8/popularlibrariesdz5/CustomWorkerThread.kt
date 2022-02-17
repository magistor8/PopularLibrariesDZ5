package com.magistor8.popularlibrariesdz5

import android.util.Log
import androidx.annotation.WorkerThread
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class CustomWorkerThread: Thread() {

    private val queue: PriorityQueue<Entity> = PriorityQueue()
    private var isLoopWorking = false
    private val lock: Lock = ReentrantLock()
    private val condition = lock.newCondition()

    data class Entity(
        val runnable: Runnable,
        val priority: Priority = Priority.NORMAL
    ) : Comparable<Entity> {
        override fun compareTo(other: Entity) : Int {
            return -priority.pr
        }
    }

    enum class Priority(val pr: Int) {
        LOW(1),
        NORMAL(2),
        HIGH(3)
    }

    @WorkerThread
    override fun run() {
        isLoopWorking = true

        while (isLoopWorking) {
            try {
                var runnable: Runnable?
                lock.withLock {
                    Log.d("@@@", "Сейчас в очереди ${queue.size} задач")
                    val entity = if (queue.size > 0)
                        queue.poll() else null
                    if (entity == null) {
                        Log.d("@@@", "Поток без задач")
                        condition.await()
                    } else {
                        Log.d("@@@", "Выполняю задачу с приоритетом ${entity.priority}")
                        entity.runnable.run()
                    }
                }
            } catch (ie: InterruptedException) {
                ie.printStackTrace()
            }
        }
    }

    fun post(runnable: Runnable, priority: Priority) {
        Log.d("@@@", "Новая задача с приоритетом $priority")
        lock.withLock {
            queue.offer(Entity(runnable, priority))
            condition.signal()
        }
    }

    fun quit() {
        isLoopWorking = false
        Log.d("@@@", "Завершено")
        lock.withLock { condition.signal() }
    }
}