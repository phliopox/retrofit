package com.ian.coru1.retrofit

import com.google.gson.JsonElement
import com.ian.coru1.utils.API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(API.SEARCH_PHOTO)
    fun searchPhotos(@Query("query") searchTerm : String) : Call<JsonElement>

    @GET(API.SEARCH_USERS)
    fun searchUsers(@Query("query") searchTerm : String) : Call<JsonElement>
}