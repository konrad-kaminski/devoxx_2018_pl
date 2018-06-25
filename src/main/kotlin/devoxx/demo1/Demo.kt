package devoxx.demo1

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.coroutines.experimental.suspendCoroutine

private val timer = Timer(true)

suspend fun mysleep(delay: Long): Unit = suspendCoroutine { callback ->
    timer.schedule(delay) {
        callback.resume(Unit)
    }
}

fun main(args: Array<String>) = runBlocking {
    val jobs = List(100_000) {
        launch {
            mysleep(5_000)
            print('.')
        }
    }

    jobs.forEach { it.join() }
}