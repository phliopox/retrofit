package com.ian.coru1.retrofit

import android.util.Log
import com.google.gson.JsonElement
import com.ian.coru1.utils.API
import com.ian.coru1.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    //http call 만들기, 레트로핏 인터페이스 가져옴
    private val httpCall: RetrofitInterface? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (String) -> Unit) {
        val term = searchTerm?:""
        val call = httpCall?.searchPhotos(searchTerm = term)?:return
        call.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "응답성공 - onResponse: ${response.body().toString()}")
                completion(response.body().toString())

            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "응답실패 - onFailure: ");
            }

        })
    }
}