package com.example.interim.ui.home.admin_view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interim.ui.home.admin_view.signalements.AdminSignalementsFragment
import com.example.interim.ui.home.admin_view.signupreview.AdminSignUpRequestsFragment

class AdminViewPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 2 // Number of fragments
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AdminSignUpRequestsFragment()
            1 -> AdminSignalementsFragment()
            else -> AdminSignUpRequestsFragment()
        }
    }

}
