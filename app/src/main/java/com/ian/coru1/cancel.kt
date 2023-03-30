package com.ian.coru1

import kotlinx.coroutines.*

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


fun main()= runBlocking {
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