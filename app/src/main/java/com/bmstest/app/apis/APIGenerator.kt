package com.bmstest.app.apis


import ApiURLs
import RetrofitAPIInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object APIGenerator {
    val apiClass: RetrofitAPIInterface
        get() {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ApiURLs.BASE_URL)
                .client(requestHeader)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RetrofitAPIInterface::class.java)
        }

    private val requestHeader: OkHttpClient
        get() = OkHttpClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build()
}