package com.ian.coru1

import kotlinx.coroutines.*

//CoroutineScope는 runBlocking 내부에서만 사용 가능
//runBlocking, coroutineScope 차이점
//coroutineScope에서는 자식 스레드가 완료될 때 까지 현재 스레드를 block 하지 않는다.
//runBlocking에서는 자식 스레드가 완료될 때 까지 현재 스레드를 block 한다.
//자식 스레드가 완료될 때 까지 현재 스레들 block 한다.

//자식 스레드가 완료될 때 까지 현재 스레드를 block한다고 하는데
//왜 아래 coroutineScope에 "Task from coroutine scope"가 먼저 실행 되는건지 궁금합니다.
fun main41(){
    runBlocking {// this: CoroutineScope
        //자식 스레드가 완료될 때 까지 현재 스레들 block 한다.
        launch {
            println("launch1")
            delay(200L)
            println( "Task from runBlocking")
        }
        coroutineScope{
            //자식 스레드
            launch {
                println("coroutineScope - launch2")
                delay(500L)
                println( "Task from nested launch")
            }
            //현재 스레드
            println( "Task from coroutine scope")
        }
        println( "Coroutine scope is over")
    }
    println( "isItBlocked? : True")

}

fun main40(){
    runBlocking {// this: CoroutineScope
        //자식 스레드가 완료될 때 까지 현재 스레들 block 한다.
        launch {
            println("launch1")
            delay(200L)
            println( "Task from runBlocking")
        }
    }
    println( "is runBlock Blocked? : True")

    CoroutineScope(Dispatchers.Default).launch{
        launch {
            println("coroutineScope - launch2")
            delay(500L)
            println( "Task from nested launch")
        }
        //현재 스레드
        println( "Task from coroutine scope")
    }
    println( "is CoroutineScope Blocked? : False")
}
fun main42() { runBlocking {
    launch {
        delay(200L)
        println("Task from runBlocking")
    }
}
    runBlocking {
    coroutineScope {
        launch {
            delay(500L)
            println("Task from nested launch")
        }
        delay(100L)
        println("Task from coroutine scope")
    }}
    println("Coroutine scope is over")
}