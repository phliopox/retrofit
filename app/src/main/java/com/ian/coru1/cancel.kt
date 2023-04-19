package com.ian.coru1

import kotlinx.coroutines.*
import kotlin.random.Random

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
/*

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
}*/

interface a{
    fun a ()
}
suspend fun printRandom() {
    println(Random.nextInt(0, 500))

}

fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch(Dispatchers.IO) {
        printRandom()

    }

    Thread.sleep(1000L)
}

internal open class parent {
    constructor(){
        println("parent")
    }

    open fun compute(num: Int): Int {
        println("parent compute")
        return if (num <= 1) num else compute(num - 1) + compute(num - 2)
    }
}

internal class Child : parent() {

    override fun compute(num: Int): Int {
        println("chlid compute")
        return if (num <= 1) num else compute(num - 1) + compute(num - 3)
    }
}

internal object good {
    @JvmStatic
    fun main(args: Array<String>) {
      /*  val obj: parent = Child()
        print(obj.compute(4))
        var a=1
        print("test ${a++}")
        println(a.toString())*/
        /*println(3%1)
        println(1%3)
        val arr = arrayOf(1, 2, 3, 4, 5)
        var i=0
        arr[i++] = 5
        println(arr.forEach { i->print(arr[i-1]) })
        println(arr[i])*/

        println(0%3)
    }
}