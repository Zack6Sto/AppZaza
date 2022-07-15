package com.example.appzaza.data.api


import android.net.Uri
import com.example.appzaza.data.model.ConfigData
import com.example.appzaza.data.model.MarketsItem
import com.example.appzaza.data.model.OnBoardingData
import com.example.appzaza.data.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }

    override suspend fun getMarkets(): List<MarketsItem> {
        return apiService.getMarkets()
    }

    override suspend fun getDataOnBoarding(): List<OnBoardingData> {
        return apiService.getDataOnBoarding()
    }

    override suspend fun getConfigApp(): Observable<Response<ConfigData>> {
//    override suspend fun getConfigApp(): List<ConfigData> {
//    override suspend fun getConfigApp(): ConfigData {
//        return apiService.getConfigApp("")
        return apiService.getConfigApp("https://firebasestorage.googleapis.com/v0/b/appa-bd08f.appspot.com/o/conf%2Fmobile_config.json?alt=media&token=4afa5bbc-abc9-4bc9-85ca-10a54f656c9e")
    }


//    override suspend fun getConfigApp(): Observable<Response<ConfigData>> {
//        val storage = Firebase.storage
//        val link = "gs://appa-bd08f.appspot.com/conf/mobile_config.json"
//
//        val gsReference = storage.getReferenceFromUrl(link)
//        gsReference.downloadUrl.addOnCompleteListener { task ->
//            if (task.isSuccessful){
//                val downUri: Uri? = task.result
//                val baseUrl = downUri.toString().split(".com")[0] + ".com"
//                val param1 = downUri.toString().split(".com")[1] + ".com" + downUri.toString()
//                    .split(".com")[2]
//            }
//        }
//
//        return apiService.getConfigApp(baseUrl+param1)
//    }
}