package com.example.appzaza.ui.main.view

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.appzaza.R
import com.example.appzaza.base.BaseActivity
import com.example.appzaza.databinding.ActivityMainBinding
import com.example.appzaza.ui.main.view.main.favorite.FavoriteFragment
import com.example.appzaza.ui.main.view.main.home.HomeFragment
import com.example.appzaza.ui.main.view.main.setting.SettingFragment
import com.example.appzaza.ui.main.viewmodel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var mainViewModel: MainViewModel

    val TAG = "MainActivity"

    override val bindLayout: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        setUIFragment()
    }

    private fun setUIFragment() {
        val homeFragment = HomeFragment()
        val favFragment = FavoriteFragment()
        val settingFragment = SettingFragment()

        setCurrentFragment(homeFragment)
        Handler().postDelayed({
            badgeSetup(R.id.nav_setting,9)
        },2000)

        badgeSetup(R.id.nav_favourites,900000)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home ->{
                    setCurrentFragment(homeFragment)
                    Log.e(TAG,"Home selected")
                    badgeClear(R.id.nav_home)
                }
                R.id.nav_favourites ->{
                    setCurrentFragment(favFragment)
                    Log.e(TAG,"Fav selected")
                    badgeClear(R.id.nav_favourites)
                }
                R.id.nav_setting ->{
                    setCurrentFragment(settingFragment)
                    Log.e(TAG,"Setting selected")
                    badgeClear(R.id.nav_setting)
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper,fragment)
            commit()
        }

    private fun badgeSetup(id: Int, alert:Int){
        val badge = binding.bottomNavigation.getOrCreateBadge(id)
            badge.isVisible = true
            badge.number = alert
    }

    private fun badgeClear(id: Int){
        val badgeDrawable = binding.bottomNavigation.getBadge(id)
        if (badgeDrawable != null){
            badgeDrawable.isVisible = false
            badgeDrawable.clearNumber()
        }
    }


}