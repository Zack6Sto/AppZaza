package com.example.appzaza.data.repository


import com.example.appzaza.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()
    suspend fun getMarkets() = apiHelper.getMarkets()
    suspend fun getDataOnBoarding() = apiHelper.getDataOnBoarding()
    suspend fun getConfigApp() = apiHelper.getConfigApp()

//    fun getConfigApp() = ApiRetrofitBuilder.instance.requestConfigMobileAppFirebase(object : ApiRetrofitBuilder.CallBackApi<String?>{
//
//            override fun respondAPI(resp: String?) {
////                callback: ApiRetrofitBuilder.CallBackApi<String?>
////                callback.respondAPI(resp)
//                Log.e("getConfigApp","respondAPI:"+resp.toString())
//            }
//
//            override fun respException(error: String?) {
////                callback.respException(error)
//                Log.e("getConfigApp","respException:"+error.toString())
//            }
//
//})
}