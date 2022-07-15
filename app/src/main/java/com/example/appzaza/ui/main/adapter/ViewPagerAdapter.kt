package com.example.appzaza.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appzaza.ui.main.view.main.home.main.Home1Fragment
import com.example.appzaza.ui.main.view.main.home.main.Home2Fragment
import com.example.appzaza.ui.main.view.main.home.main.Home3Fragment

private const val NUM_TABS = 4
private const val ARG_OBJECT = "object"

class ViewPagerAdapter(fmManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fmManager,lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return Home1Fragment()
            1 -> return Home2Fragment()
        }
        return Home3Fragment()
    }

}

//class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
//
//    override fun getItemCount(): Int = 100
//
//    override fun createFragment(position: Int): Fragment {
//        // Return a NEW fragment instance in createFragment(int)
//        val fragment = Home1Fragment()
//        fragment.arguments = Bundle().apply {
//            // Our object is just an integer :-P
//            putInt(ARG_OBJECT, position + 1)
//        }
//        return fragment
//    }
//}