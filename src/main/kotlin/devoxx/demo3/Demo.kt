package devoxx.demo3

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.Timer
import kotlin.concurrent.schedule
import kotlin.coroutines.experimental.suspendCoroutine
import kotlin.system.measureTimeMillis

private val timer = Timer(true)

suspend fun mysleep(delay: Long): Unit = suspendCoroutine { callback ->
    timer.schedule(delay) {
        callback.resume(Unit)
    }
}

class PersonalDetails(val name: String)
class FinancialDetails(val amount: Long)

suspend fun loadPersonalDetails(): PersonalDetails {
    mysleep(2_000)
    return PersonalDetails("John")
}

suspend fun loadFinancialDetails(): FinancialDetails {
    mysleep(4_000)
    return FinancialDetails(120)
}

fun main(args: Array<String>) = runBlocking {
    val timeMillis = measureTimeMillis {
        val personalDetails = async { loadPersonalDetails() }
        val financialDetails = async { loadFinancialDetails() }

        println("name = ${personalDetails.await().name}, " +
                "amount = ${financialDetails.await().amount}")
    }

    println ("time = $timeMillis")
}