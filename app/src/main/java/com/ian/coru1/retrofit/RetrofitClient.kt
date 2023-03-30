package com.ian.coru1.retrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.ian.coru1.App
import com.ian.coru1.utils.API
import com.ian.coru1.utils.Constants.TAG
import com.ian.coru1.utils.isJsonArray
import com.ian.coru1.utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//싱글톤 (object)
object RetrofitClient {
    //레트로칫 클라이언트 선언
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient: ");

        //로깅 인터센터 추가
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(object :HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d(TAG, "RetrofitClient - log: $message");
                when{
                    message.isJsonObject() ->{
                        Log.d(TAG, "json object ");
                    }
                    message.isJsonArray()->{
                        Log.d(TAG, "jsonArray ");
                    }
                    else->{
                        //try-catch
                    }
                }
            }

        })
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client.addInterceptor(loggingInterceptor)

        //기본 파라미터 추가
        val baseParameterInterceptor : Interceptor = (object: Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                Log.d(TAG, "RetrofitClient - intercept: ");
                val originalRequest =chain.request()
                val addedUrl =
                    originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
                val finalRequest =
                    originalRequest.newBuilder()
                        .url(addedUrl)
                        .method(originalRequest.method,originalRequest.body)
                        .build()
                //return chain.proceed(finalRequest)
                val response = chain.proceed(finalRequest)
                if (response.code != 200) {
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(App.instance,"${response.code} 입니다",Toast.LENGTH_SHORT).show()
                    }
                }
                return response
            }
        })

        //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
        client.addInterceptor(baseParameterInterceptor)

        //커넥션 타임아웃
        client.connectTimeout(10,TimeUnit.SECONDS)
        client.readTimeout(10,TimeUnit.SECONDS)
        client.writeTimeout(10,TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}