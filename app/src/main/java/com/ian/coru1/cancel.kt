package com.ian.coru1

import com.ian.coru1.model.Photo
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main11()= runBlocking {
    val job= launch {
        repeat(1000) { i->
            println("job : I'm sleeping $i ...")
            delay(500)
        }
    }
    delay(1300)
    println("main : Im tried of waiting!")
    job.cancel()
    job.join()

    println("main : Now I can quit")
}


fun main12()= runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while(i<5){
            if (System.currentTimeMillis() >= nextPrintTime) {
                yield()
                println("job : I'm sleeping ${i++}...")
                nextPrintTime += 500L

            }
        }
    }

    delay(1300)
    println("main : Im tried of waiting!")
    job.cancelAndJoin() // 캔슬이 잘 되려면 suspend 함수를 코루틴 내부에 작성해야한 -> yield()
    println("main : Now I can quit")
}


fun main5()= runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while(isActive){ // yield() 대신
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job : I'm sleeping ${i++}...")
                nextPrintTime += 500L
            }
        }
    }

    delay(1300)
    println("main : Im tried of waiting!")
    job.cancelAndJoin()
    println("main : Now I can quit")
}

/*suspend fun doCount() = coroutineScope {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextTime = System.currentTimeMillis() +100L

        while(i<=10&& isActive){
            val currentTime = System.currentTimeMillis()
            if(currentTime >= nextTime){
                println(i)
                nextTime = currentTime +100L
                i++
            }
        }
    }
    delay(200L)
    job.cancelAndJoin()
    println("doCount Done!")
}*/
//timeOut 설절하기
/*
suspend fun doCount() = coroutineScope {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var nextTime = System.currentTimeMillis() +100L

        while(i<=10&& isActive){
            val currentTime = System.currentTimeMillis()
            if(currentTime >= nextTime){
                println(i)
                nextTime = currentTime +100L
                i++
            }
        }
    }
}
fun main () = runBlocking{
    val result = withTimeoutOrNull(500L){
        doCount()
        true
    } ?:false
    println(result)
}*/

suspend fun getRandom1(): Int {
    delay(1000L)
    return Random.nextInt(0, 500)
}

suspend fun getRandom2(): Int {
    delay(1000L)
    return Random.nextInt(0, 500)
}
suspend fun getRandom3(): Photo {
    delay(1000L)
    return Photo("a","b","c",5)
}


fun main() = runBlocking {
    val elapsedTime = measureTimeMillis {
        val value1 = async {  getRandom1() }
        val value2 = async {  getRandom2() }
        val value3 = async { getRandom3() }
        println("${value1.await()} + ${value2.await()} = ${value1.await() + value2.await()}")
        println("${value3.await()}")
    }
    println(elapsedTime)
}