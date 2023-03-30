package com.ian.coru1.retrofit

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.JsonElement
import com.ian.coru1.Photo
import com.ian.coru1.utils.API
import com.ian.coru1.utils.Constants.TAG
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    //http call 만들기, 레트로핏 인터페이스 가져옴
    private val httpCall: RetrofitInterface? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)

    //사진검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (ArrayList<Photo>) -> Unit) {
        val term = searchTerm ?: ""
        val call = httpCall?.searchPhotos(searchTerm = term) ?: return
        call.enqueue(object : retrofit2.Callback<JsonElement> {
            @SuppressLint("SimpleDateFormat")
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "응답성공 - onResponse: ${response.body().toString()}")
                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            var parsedPhotoDataArray = ArrayList<Photo>()
                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")
                            val total = body.get("total").asInt // 검색결과 갯수

                            results.forEach { resultItem ->
                                val asJsonObject = resultItem.asJsonObject
                                val user = asJsonObject.get("user").asJsonObject
                                val username = user.get("username").asString
                                val likesCount = asJsonObject.get("likes").asInt
                                val thumbnailLink =
                                    asJsonObject.get("urls").asJsonObject.get("thumb").asString
                                val createdAt = asJsonObject.get("created_at").asString
                                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                val outputDateString = formatter.format(parser.parse(createdAt))
                                val photoItem = Photo(
                                    author = username,
                                    likeCount = likesCount,
                                    thumbnail = thumbnailLink,
                                    createdAt = outputDateString
                                )

                                parsedPhotoDataArray.add(photoItem)
                            }
                            completion(parsedPhotoDataArray)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "응답실패 - onFailure: ");
            }

        })
    }
}