package com.example.appzaza.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {
    private const val BASE_URL = "https://5e510330f2c0d300147c034c.mockapi.io/"
//    private const val BASE_URL = "gs://appa-bd08f.appspot.com/conf/mobile_config.json"

//    private const val BASE_URL = "https://firebasestorage.googleapis.com"
//    private const val url = "https://api.coingecko.com/api/v3/"

    private var gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}