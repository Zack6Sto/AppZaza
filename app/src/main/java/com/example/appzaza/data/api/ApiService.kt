package com.example.appzaza.data.api

import com.example.appzaza.data.model.ConfigData
import com.example.appzaza.data.model.MarketsItem
import com.example.appzaza.data.model.OnBoardingData
import com.example.appzaza.data.model.User
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("/coins/markets")
    suspend fun getMarkets(): List<MarketsItem>

    suspend fun getDataOnBoarding(): List<OnBoardingData>

    @GET
//    suspend fun getConfigApp(@Url endpoint: String): List<ConfigData>
//    suspend fun getConfigApp(@Url endpoint: String): ConfigData
    fun getConfigApp(@Url endpoint: String): Observable<Response<ConfigData>>
//    suspend fun getConfigApp(): Observable<Response<ResponseBody>>

    @GET
    fun requestConfigApp(@Url endpoint: String): Observable<Response<ResponseBody>>
}