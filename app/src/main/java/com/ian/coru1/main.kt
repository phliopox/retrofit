package com.ian.coru1

import kotlinx.coroutines.*

fun main1() {
    // 다른 코드 수행을 막음 runblocking ,
    //launch 는 새로운 코루틴을 생성하고, 다른 코드의 수행을 기다리지 않음(runblocking 내부에서 사용시에는 runblocking 코드를 기다림.)
    runBlocking {
        GlobalScope.launch {
            delay(1000)
            println("world!!")
        }
        println("Hello,")
        delay(2000)
    }
    //Thread.sleep(2000)
}

fun main2() {
    runBlocking {
        val job = GlobalScope.launch {
            delay(1000)
            println("world!!")
        }
        println("hello")
        job.join()
    }
}

fun main3() = runBlocking {
    launch {
        myWorld()
    }
    launch {
        myWorld()
    }
    println("hello")
}

suspend fun myWorld() {
    delay(1000)
    println("world!!")
}

fun main123() = runBlocking {
    launch {
        repeat(5) { i ->
            println("라라라 $i")
            delay(10)
        }
    }
    launch {
        repeat(5) { i ->
            println("룰룰루 $i")
            delay(10)
        }
    }
    println("이게 가장 먼저 실행")
}

suspend fun doOneTwoThree() = coroutineScope {
    val job = launch { // launch는 반드시 코루틴 안에서 호출되어야한다.
        println("launch 1")
        delay(1000L)
        println("3!")
    }
    job.join() //suspension point
    launch {
        println("launch 2")
        println("1!")
    }
    launch {
        println("launch 3")
        delay(500L)
        println("2!")
    }
    println("4!")
}

/*
fun main4() { runBlocking{
    val job = launch{ // launch는 반드시 코루틴 안에서 호출되어야한다.
        println("launch 1")
        delay(1000L)
        println("3!")
    }
    job.join() //suspension point
    launch{
        println("launch 2")
        println("1!")
    }
    launch{
        println("launch 3")
        delay(500L)
        println("2!")
    }}
    println("4!")
}
*/

