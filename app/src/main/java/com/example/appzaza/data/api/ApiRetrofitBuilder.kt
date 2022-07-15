package com.example.appzaza.data.api

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import com.example.appzaza.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiRetrofitBuilder{
    init {
        println("This ($this) is a singleton")
    }

    private object Holder {
        val INSTANCE = ApiRetrofitBuilder()
    }

    companion object {
        lateinit var retrofit: Retrofit

        val instance: ApiRetrofitBuilder by lazy { Holder.INSTANCE }
    }

    interface CallBackApi<T> {
        fun respondAPI(resp: T)
        fun respException(error: String?)
    }

    private fun getService(url: String): ApiService {
        val client = createClient()

//        if (url.contains(":17381")) {
//            client = Unsafe_CA_SSL.getUnsafeOkHttpClient()
//        }


        retrofit = Retrofit.Builder().baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

            .build()

        return retrofit.create(ApiService::class.java)

    }

    /**
    internal var OKHttp: Create? = null internal
    var with: client? = null internal
    var Interceptor: Logging? = null.
     *
    internal var production: On? = null internal
    var have: not? = null internal
    var interceptor: add? = null
     *
     * @ return OkHttpClient
     */
    private fun createClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY

                    })
                }
            }


        return client.build()
    }

    private fun requestConfigMobile(url: String, param: String): Observable<Response<ResponseBody>> {
        return getService(url)
            .requestConfigApp(param)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .unsubscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    fun requestConfigMobileAppFirebase(callback: CallBackApi<String?>) {
        val storage = Firebase.storage

        // Create a reference to a file from a Google Cloud Storage URI
//        val link = if (BuildConfig.FLAVOR.contains("Test")) {
//            "gs://testprofis-247be.appspot.com/conf/mobile_new_config.json"
//        } else {
//            "gs://ictprofit-d706e.appspot.com/conf/mobile_new_config.json"
//        }
        val link = "gs://appa-bd08f.appspot.com/conf/mobile_config.json"

        val gsReference = storage.getReferenceFromUrl(link)
        gsReference.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downUri: Uri? = task.result
                val baseUrl = downUri.toString().split(".com")[0] + ".com"
                val param1 = downUri.toString().split(".com")[1] + ".com" + downUri.toString()
                    .split(".com")[2]

                requestConfigMobile(baseUrl, baseUrl + param1).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res: Response<ResponseBody>? ->


                        if (res?.isSuccessful == true) {
                            res.body()?.string()?.let { callback.respondAPI(it) }
                        } else {
                            callback.respException(res?.message())
                        }
                    }, { e ->
                        callback.respondAPI(null.toString())
                        callback.respException(e.message)
                    })


            } else {
                Log.e("requestListBroker", "error")
                callback.respException(task.exception?.message ?: "error")
            }
        }
    }
}