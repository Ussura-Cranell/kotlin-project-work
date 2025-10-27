package core.Testing.MultitaskTesting

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.thread
import kotlin.coroutines.*
import kotlin.time.Duration.Companion.seconds

suspend fun multitaskTesting(){

    println("Начало работы с потоками")

    thread {
        var i: Int = 1
        repeat(5){
            println("#1.${i++}. Работа 1 потока")
            Thread.sleep(250)
        }
    }

    runBlocking { something(3) }
    // launch {something(4)}
    // something()

    thread {
        var i: Int = 1
        repeat(5){
            println("#2.${i++}. Работа 2 потока")
            Thread.sleep(200)
        }
    }

    runBlocking {

        runBlocking {
            launch(Dispatchers.Default  + CoroutineName("10000 in 1")) {
                var x = 0
                repeat(10000) {
                    x++
                }
                println("10000 операций в одной корутине: $x")
                println(coroutineContext)
            }
        }

        runBlocking {
            var x = 0
            repeat(10000) {
                launch(Dispatchers.Default) {
                    // var x = 0
                    x++
                }
            }
            delay(1.seconds)
            println("1 операция в 10000 корутинах: $x (проблема синхронизации)")
        }

        runBlocking {
            val mutex: Mutex = Mutex()
            var x = 0
            repeat(10000) {
                val cdispetcher: CoroutineContext = Dispatchers.Default
                launch(cdispetcher) {
                    // var x = 0
                    mutex.withLock {
                        x++
                    }
                }
            }
            delay(1.seconds)
            println("1 операция в 10000 корутинах: $x (синхронизированная)")
        }

        println(coroutineContext)

    }

    println("Завершение работы с потоками")
}


internal suspend fun something(num: Int){
    var i = 1
    repeat(5) {
        println("#3.${i++} Работа $num потока")
        Thread.sleep(100)
    }
}