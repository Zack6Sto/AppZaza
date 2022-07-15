package com.example.appzaza.data.api

import com.example.appzaza.data.model.ConfigData
import com.example.appzaza.data.model.MarketsItem
import com.example.appzaza.data.model.OnBoardingData
import com.example.appzaza.data.model.User
import io.reactivex.Observable
import retrofit2.Response

interface ApiHelper {
    suspend fun getUsers():List<User>
    suspend fun getMarkets():List<MarketsItem>
    suspend fun getDataOnBoarding():List<OnBoardingData>
//    suspend fun getConfigApp():ConfigData
//    suspend fun getConfigApp():List<ConfigData>
    suspend fun getConfigApp():Observable<Response<ConfigData>>
}