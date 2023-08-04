package com.example.appzaza.ui.main.view.main.home.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.appzaza.base.BaseFragment
import com.example.appzaza.databinding.FragmentHome2Binding
import com.example.appzaza.ui.main.viewmodel.RemoteConfigViewModel


private const val ARG_OBJECT = "object"

class Home2Fragment : BaseFragment<FragmentHome2Binding>() {

//    private var mFirebaseRemoteConfig: FirebaseRemoteConfig? = null
//    private var remoteConfigViewModel : RemoteConfigViewModel? = null
private val remoteConfigViewModel: RemoteConfigViewModel by viewModels()
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHome2Binding
        get() = FragmentHome2Binding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initData()
        initRemoteConfig()
    }

    private fun initData() {

    }

    private fun initRemoteConfig() {
        binding.btnFetch.setOnClickListener {
//            fetchDiscount()
        }
    }

//    private fun fetchDiscount() {
//        remoteConfigViewModel.fetchDiscount().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                displayPrice()
//            } else {
//                Log.d("FETCH", "Failed")
//            }
//        }
//    }
//
//    private fun displayPrice() {
//        val initialPrice = remoteConfigViewModel.getInitialPrice()
//        var finalPrice = initialPrice
//
//        if (remoteConfigViewModel.isPromotionOn()) {
//            finalPrice = initialPrice - remoteConfigViewModel.getDiscount()
//        }
//        binding.tvEx.text = finalPrice.toString()
//    }

}