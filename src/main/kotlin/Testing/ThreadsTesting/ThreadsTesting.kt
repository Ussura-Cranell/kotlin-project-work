package core.Testing.ThreadsTesting

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.milliseconds

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.time.Duration.Companion.seconds

suspend fun threadsTesting() {
    runBlocking {

        val flowList = createValues2()
        flowList.collect { println("flowList: $it") }

        val list2 = createValues()
        println(println("list2: $list2"))

    }

    println("Flow channel:")
    runBlocking {
        val randomNumbersFlowChannel = channelFlow {
            repeat(50){
                launch {
                    delay(2.seconds)
                    send(generateValue())
                }
            }
        }
        randomNumbersFlowChannel.collect{print("${it} ")}
        println()

        /*launch {
            while(true){
                delay(250.milliseconds)
                println("test")
            }
        }*/
    }

    runBlocking {
        val radioStation = RadioStation()
        radioStation.beginBroadcasting(this)
        delay(600.milliseconds)

        launch { radioStation.messageFlow.collect { println("Receiver 1: $it") } }
        launch { radioStation.messageFlow.collect { println("Receiver 2: $it") } }
        launch { radioStation.messageFlow.collect { println("Receiver 3: $it") } }

    }
}

internal suspend fun createValues(): List<Int>{
    var i = 0
    return buildList {
        repeat(10){add(i++); delay(250.milliseconds)}
    }
}

internal suspend fun createValues2(): Flow<Int>{
    var i = 0
    return flow {
            repeat(10){emit(i++); delay(500.milliseconds)}
    }
}

var iter = 0
internal suspend fun generateValue(): Int{
    return iter++//Random.nextInt(0, 100)
}

class RadioStation {
    private val _messageFlow = MutableSharedFlow<Int>()
    val messageFlow = _messageFlow.asSharedFlow()

    fun beginBroadcasting(scope: CoroutineScope){
        scope.launch {
            while(true){
                delay(500.milliseconds)
                val num = Random.nextInt(0..10)
                println("Generate: ${num}")
                _messageFlow.emit(num)
            }
        }
    }
}




