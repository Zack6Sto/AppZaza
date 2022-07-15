package com.example.appzaza.ui.main.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.example.appzaza.R
import com.example.appzaza.data.api.ApiHelperImpl
import com.example.appzaza.data.api.RetrofitBuilder
import com.example.appzaza.data.model.OnBoardingData
import com.example.appzaza.databinding.ActivitySplashBinding
import com.example.appzaza.ui.main.adapter.OnBoardingViewPagerAdapter
import com.example.appzaza.ui.main.intent.MainIntent
import com.example.appzaza.ui.main.view.MainActivity
import com.example.appzaza.ui.main.view.login.LoginActivity
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import com.example.appzaza.ui.main.viewstate.MainState
import com.example.appzaza.util.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sharePreferences: SharedPreferences

    var onBoardingViewPagerAdapter: OnBoardingViewPagerAdapter? =null
    var onBoardingViewPager: ViewPager? = null
    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (restorePrefData()){
            val i = Intent(applicationContext,LoginActivity::class.java)
            startActivity(i)
            finish()
        }

        supportActionBar?.hide()
        setDataOnBoarding()
//        setupViewModel()
//        observeViewModel()
    }

    private fun setupViewModel() {
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
            mainViewModel.userIntent.send(MainIntent.FetchDataOnBoarding)
            mainViewModel.state.collect {
                when(it){
                    is MainState.Idle ->{}
                    is MainState.Loading ->{}
                    is MainState.Users ->{}
                    is MainState.Error ->{
                        Toast.makeText(this@SplashActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    is MainState.DataOnBoarding ->{
//                        setDataOnBoarding()
                        setOnBoardingViewPagerAdapter(it.onBoarding)
                    }
                    is MainState.ConfigApp -> TODO()
                    is MainState.Markets -> TODO()
                }
            }
        }
    }

    private fun setOnBoardingViewPagerAdapter(onBoardingData: List<OnBoardingData>){
        onBoardingViewPager = findViewById(R.id.screenPager)
        onBoardingViewPagerAdapter = OnBoardingViewPagerAdapter(this,onBoardingData)
        onBoardingViewPager!!.adapter = onBoardingViewPagerAdapter
        binding.tabIndicator.setupWithViewPager(onBoardingViewPager)

        setOnClicks(onBoardingData)
    }

    private fun setOnClicks(onBoardingData: List<OnBoardingData>) {
        position = onBoardingViewPager!!.currentItem

        binding.tvNext.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                savePrefData()
                val i = Intent(applicationContext,LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }


        binding.tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size -1){
                    binding.tvNext.text = "Finish"
                }else{
                    binding.tvNext.text = "Next"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun setDataOnBoarding() {

        val onBoardingData: MutableList<OnBoardingData> = ArrayList()
        onBoardingData.add(OnBoardingData("Pager 1","Test 1",R.drawable.ic_launcher_foreground))
        onBoardingData.add(OnBoardingData("Pager 2","Test 2",R.drawable.ic_launcher_foreground))
        onBoardingData.add(OnBoardingData("Pager 3","Test 3",R.drawable.ic_launcher_foreground))

        setOnBoardingViewPagerAdapter(onBoardingData)

        position = onBoardingViewPager!!.currentItem

        binding.tvNext.setOnClickListener {
            if (position < onBoardingData.size) {
                position++
                onBoardingViewPager!!.currentItem = position
            }
            if (position == onBoardingData.size){
                savePrefData()
                val i = Intent(applicationContext,LoginActivity::class.java)
                startActivity(i)
                finish()
            }
        }


        binding.tabIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position
                if (tab.position == onBoardingData.size -1){
                    binding.tvNext.text = "Get Started"
                }else{
                    binding.tvNext.text = "Next"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }


    private fun savePrefData(){
        sharePreferences = applicationContext.getSharedPreferences("pref", MODE_PRIVATE)
        val edit : SharedPreferences.Editor = sharePreferences.edit()
        edit.putBoolean("isFirstTimeRun",true)
        edit.apply()
    }

    private fun restorePrefData():Boolean{
        sharePreferences = applicationContext.getSharedPreferences("pref", MODE_PRIVATE)
        return sharePreferences.getBoolean("isFirstTimeRun",false)
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    private fun initAnim() {
//        val topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation)
//        val middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation)
//        val bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)
//
//        binding.tv1.startAnimation(topAnimation)
//        binding.tv2.startAnimation(middleAnimation)
//        binding.tv3.startAnimation(bottomAnimation)
//
//
//        val splashTimeOut = 4000
//        Handler().postDelayed({
//            startActivity(Intent(this,LoginActivity::class.java))
//            finish()
//        },splashTimeOut.toLong())
//    }
}