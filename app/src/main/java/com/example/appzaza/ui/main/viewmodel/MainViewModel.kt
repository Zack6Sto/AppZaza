package com.example.appzaza.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appzaza.data.repository.MainRepository
import com.example.appzaza.ui.main.intent.MainIntent
import com.example.appzaza.ui.main.viewstate.MainState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val repository: MainRepository)
    :ViewModel(){
    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state
    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.FetchUser ->{

                        fetchUser(it.textShow)
                    }
                    is MainIntent.FetchMarkets -> fetchMarkets()
                    is MainIntent.FetchDataOnBoarding -> fetchDataOnBoarding()
                    is MainIntent.FetchConfigApp -> fetchConfigApp()
                }
            }
        }
    }

    private fun fetchUser(string: String) {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                Log.e("fetchUser:",string)
                MainState.Users(repository.getUsers())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }

    private fun fetchMarkets() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Markets(repository.getMarkets())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }

    private fun fetchDataOnBoarding(){
        viewModelScope.launch {
            _state.value = try {
                MainState.DataOnBoarding(repository.getDataOnBoarding())
            } catch (e: Exception){
                MainState.Error(e.localizedMessage)
            }
        }
    }


//    private fun fetchConfigApp() {
//        viewModelScope.launch {
//            _state.value = MainState.Loading
//            _state.value = try {
////                MainState.ConfigApp(repository.getConfigApp())
//                MainState.ConfigApp(repository.getConfigApp())
//            } catch (e: Exception) {
//                MainState.Error(e.localizedMessage)
//            }
//        }
//    }

    private fun fetchConfigApp() {
        viewModelScope.launch {
            _state.value = MainState.Loading
               repository.getConfigApp().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe ({
                   if (it.isSuccessful){
                       _state.value = MainState.ConfigApp(it.body())
                   }else{
                       MainState.Error(it.message())
                   }
               },{
                   MainState.Error(it.localizedMessage)
               })
        }
    }

}