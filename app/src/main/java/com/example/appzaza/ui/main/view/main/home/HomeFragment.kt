package com.example.appzaza.ui.main.view.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.appzaza.base.BaseFragment
import com.example.appzaza.data.api.ApiHelperImpl
import com.example.appzaza.data.api.RetrofitBuilder
import com.example.appzaza.databinding.FragmentHomeBinding
import com.example.appzaza.ui.main.adapter.ViewPagerAdapter
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import com.example.appzaza.ui.main.viewstate.MainState
import com.example.appzaza.util.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
private const val TAG = "HomeFragment"
@ExperimentalCoroutinesApi
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

//    val configData: List<ConfigData>
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        setViewModel()
        observeViewModel()
        setLoadConfig()
//        initViewPager()
    }

    val animalsArray = arrayOf(
        "Cat",
        "Dog",
        "Bird"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
//        viewPagerAdapter = ViewPagerAdapter(this)
        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout
        viewPager.adapter = viewPagerAdapter

        viewPager.isSaveEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

//    private fun initViewPager() {
//        val viewPager = binding.viewPager
//        val tabLayout = binding.tabLayout
//
//        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
//        viewPager.adapter = adapter
//
//        TabLayoutMediator(tabLayout,viewPager){tab,position ->
////            tab.text = configData[0].RSS[0].index
//            tab.text = animalsArray[position]
//        }.attach()
//    }

    private fun setViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        )[MainViewModel::class.java]
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when(it){
                    is MainState.Idle ->{

                    }
                    is MainState.Loading ->{
//                        binding.progressBar.visibility = View.VISIBLE
                        Log.e(TAG,"observeViewModel Loading")
                    }

                    is MainState.Users ->{
//                        renderList(it.user)
                        Log.e(TAG,"observeViewModel Users")
                    }

                    is MainState.Error ->{
//                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"observeViewModel Error ${it.error}")
                    }
                    is MainState.DataOnBoarding -> TODO()
                    is MainState.ConfigApp -> {
//                        renderDataConfig(listOf(it.configApp))
//                        renderDataConfig(it.configApp)
                        Log.e(TAG,"observeViewModel ConfigApp")
                    }
                    is MainState.Markets -> TODO()
                }
            }
        }
    }

    private fun setLoadConfig() {
//        lifecycleScope.launch {
//            mainViewModel.userIntent.send(MainIntent.FetchConfigApp)
//        }
    }

//    private fun renderDataConfig(config: List<ConfigData?>) {
//        binding.progressBar.visibility = View.GONE
////        initViewPager()
//        val viewPager = binding.viewPager
//        val tabLayout = binding.tabLayout
//        val adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
//        viewPager.adapter = adapter
//
//        TabLayoutMediator(tabLayout,viewPager){tab,position ->
//            tab.text = config[0]?.RSS?.get(0)?.index
//        }.attach()
//
//        Log.e(TAG,"renderDataConfig: $config")
//    }
}