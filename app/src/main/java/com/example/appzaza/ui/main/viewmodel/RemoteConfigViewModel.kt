package com.example.appzaza.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appzaza.data.model.Config
import com.example.appzaza.data.model.ConnProperties
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RemoteConfigViewModel : ViewModel() {

    private val _observableUrl: MutableLiveData<String> = MutableLiveData()
    val configURL: LiveData<String> = _observableUrl

    private val _observableConfig: MutableLiveData<Config> = MutableLiveData()
    val config: LiveData<Config> = _observableConfig

    fun loadRemoteConfigParameters() {

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 100
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.apply {
            fetchAndActivate()
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
//                         ดึงค่า URL จากตัวกำหนดค่า
//                        val ip = this.getString("URL")
//                        _observableUrl.postValue(ip)
//
//                        // อัปเดตค่า IP ใน ConnProperties
//                        ConnProperties.initialize(ip) // อัปเดตค่า IP ใน ConnProperties

                        //
                        val config = Config(
                            host = this.getString("Host_Android")
                        )
                        _observableConfig.postValue(config)
                        ConnProperties.initialize(config.host)


                        Log.e("remoteConfig 1", task.isSuccessful.toString())
                        Log.e("remoteConfig 1 data", config.host)
                        Log.e("remoteConfig 1 exception", task.exception.toString())

                    } else {
                        Log.e("remoteConfig 2", task.isSuccessful.toString())
                        Log.e("remoteConfig 1 exception", task.exception.toString())

                    }
                }

            this.addOnConfigUpdateListener(object : ConfigUpdateListener {
                override fun onUpdate(configUpdate: ConfigUpdate) {
                    if (configUpdate.updatedKeys.contains("Host_Android")) {
                        remoteConfig.activate().addOnCompleteListener {
                            if (it.result){
                                val data = this@apply.getString("Host_Android")
                                Log.e("_observableConfig update1", "$data")
                            }
                            Log.e("_observableConfig update2", "${it.result}")
                        }
                    }
                    Log.e("_observableConfig update3", "$configUpdate")
                }

                override fun onError(error: FirebaseRemoteConfigException) {
                    Log.e("_observableConfig update error", "${error.message}")
                }

            })
        }
    }
}
