package core.Testing.StructuredCompete

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun structuredCompete(){

    val sum = coroutineScope {
        val a = async { generateValue() }
        val b = async { generateValue() }
        println("a: $a.")
        println("b: $b")
        a.await() + b.await()
    }

    println("Результат сложения a и b: $sum")

    try {
        runBlocking(context1) {
            launch {
                val x: Int? = 1
                val y: Int? = 2
                println(x!! + y!!)
            }
            launch {
                val x: Int? = null
                val y: Int? = 2
                println(x!! + 0)
            }
            launch {
                val x: Int? = 3
                val y: Int? = 5
                println(x!! + y!!)
            }
        }
    } catch (e: Exception){
        println("Exception: ${e}")
        e.stackTrace.forEach { println(it) }
    }

    try {
        runBlocking(context2) {
            launch {
                val x: Int? = 1
                val y: Int? = 2
                println(x!! + y!!)
            }
            launch {
                val x: Int? = null
                val y: Int? = 2
                println(x!! + 0)
            }
            launch {
                val x: Int? = 3
                val y: Int? = 5
                println(x!! + y!!) // не отображается в выводе
            }
        }
    } catch (e: Exception){
        println("Exception: ${e}")
        e.stackTrace.forEach { println(it) }
    }

    delay(1.milliseconds)
    println()

    runBlocking(Dispatchers.Default) { // ok
        var a = 0
        val mutex = Mutex()
        repeat(25){
            launch {
                mutex.withLock {
                    val i = increment(a)
                    a = i
                }

            }
        }
        delay(1.milliseconds)
        println("a1: ${a}")
    }

    delay(1.milliseconds)
    println()

    runBlocking { // fail
        var a = 0
        repeat(25){
            // запускается 25 корутин и каждый пытается изменить `a` после получения значения
            // async он именно для методов, которые могут быть приостановленны
            launch {
                val i = async { increment(a) }
                a = i.await()
            }
        }
        delay(10.milliseconds)
        println("a2: ${a} ")
        launch {

            val list: MutableList<Job> = mutableListOf()

            list += launch(Dispatchers.Default + CoroutineName("launch 1"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 2"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 3"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 4"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 5"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 6"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 7"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 8"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 9"))  { delay(1.seconds) }
            list += launch(Dispatchers.Default + CoroutineName("launch 10")) { delay(1.seconds) }

            list.forEachIndexed { i, job -> if (i < 6) job.cancel("ban") }

            println("job: ${coroutineContext.job}")
            println("parent: ${coroutineContext.job.parent?.job ?: "none"}")
            coroutineContext.job.children.forEach { println("clildren: ${it.job}") }
        }

    }

    delay(1.milliseconds)
    println()

    runBlocking { // fail
        var super_a:  AtomicInteger = AtomicInteger(0)
        repeat(25){
            launch {
                super_a =  AtomicInteger(increment(super_a.toInt()))
            }
        }
        println("a3: ${super_a} ")
    }

    runBlocking() {
        var value: Int? = null
        val timer = 3
        try {
            value =
                withTimeout(timer.seconds) {
                    calculate(4.seconds)
                }

        }
        catch (e: TimeoutCancellationException){
            println("${e.message}")
        }
        println("calculate: ${value} (timer $timer seconds)")
    }

    runBlocking() {
        var value: Int? = null
        val timer = 2
        try {
            value =
                withTimeout(timer.seconds) {
                    calculate(100.milliseconds)
                }

        }
        catch (e: TimeoutCancellationException){
            println("${e.message}")
        }
        println("calculate: ${value} (timer $timer seconds)")
    }


}

internal suspend fun generateValue(): Int {
    val i = Random.nextInt(0, 100)
    print("$i ")
    delay(100.milliseconds)
    return i
}

internal suspend fun calculate(delay: Duration): Int{
    delay(delay)
    return 4+2
}

internal suspend fun increment(a: Int): Int = a + 1

internal val context1 = Dispatchers.Default + SupervisorJob()
internal val context2 = Dispatchers.Default

