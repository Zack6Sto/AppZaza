package com.example.appzaza.ui.main.intent

sealed class MainIntent {
    class FetchUser(val textShow: String) : MainIntent()
    object FetchMarkets: MainIntent()
    object FetchDataOnBoarding: MainIntent()
    object FetchConfigApp: MainIntent()
}