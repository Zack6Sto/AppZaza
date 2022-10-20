package com.example.appzaza.ui.main.view.main.home.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.appzaza.base.BaseFragment
import com.example.appzaza.databinding.FragmentHome2Binding


private const val ARG_OBJECT = "object"

class Home2Fragment : BaseFragment<FragmentHome2Binding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHome2Binding
        get() = FragmentHome2Binding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initData()
    }

    private fun initData() {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            binding.text1.text = getInt(ARG_OBJECT).toString()
        }
    }
}