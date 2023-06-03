package com.example.interim.ui.statistiques

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.interim.ui.home.employer_view.EmployerCandidaturesFragment
import com.example.interim.ui.home.employer_view.EmployerOffresFragment

class StatistiquesViewPageAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {
    companion object {
        private const val NUM_PAGES = 2 // Number of fragments
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OffreStatistiquesFragment()
            1 -> UserStatistiquesFragment()
            else -> OffreStatistiquesFragment()
        }
    }
}