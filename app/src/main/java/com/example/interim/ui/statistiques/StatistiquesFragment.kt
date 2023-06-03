package com.example.interim.ui.statistiques

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.interim.R
import com.example.interim.databinding.FragmentStatistiquesBinding
import com.example.interim.ui.dashboard.DashboardViewModel
import com.google.android.material.tabs.TabLayout

class StatistiquesFragment: Fragment() {


    private var _binding: FragmentStatistiquesBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_statistiques, container, false)


        val tabs = view.findViewById<TabLayout>(R.id.tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)

        val adapter = StatistiquesViewPageAdapter(this)
        viewPager.adapter = adapter

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    tabs.selectTab(tabs.getTabAt(position))
                }
            }
        )

        return view
    }


}