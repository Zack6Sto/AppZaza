package com.example.appzaza.ui.main.viewstate

import com.example.appzaza.data.model.ConfigData
import com.example.appzaza.data.model.MarketsItem
import com.example.appzaza.data.model.OnBoardingData
import com.example.appzaza.data.model.User

sealed class MainState {
    object  Idle:MainState()
    object Loading: MainState()
    data class Users(val user: List<User>): MainState()
    data class Markets(val markets: List<MarketsItem>): MainState()
    data class DataOnBoarding(val onBoarding: List<OnBoardingData>): MainState()
//    data class ConfigApp(val configApp: List<ConfigData?>): MainState()
//    data class ConfigApp(val configApp: ArrayList<ConfigData?>): MainState()
    data class ConfigApp(val configApp: ConfigData?): MainState()

    data class Error(val error: String?): MainState()
}