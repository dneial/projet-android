package com.example.interim.ui.home.user_view

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class WorkerViewPageAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    companion object {
        private const val NUM_PAGES = 2 // Number of fragments
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UserCandidaturesFragment()
            1 -> UserOffresEnregistreesFragment()
            else -> UserCandidaturesFragment()
        }
    }
}
