package com.example.interim.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.interim.R
import com.example.interim.ui.home.admin_view.AdminViewPageAdapter
import com.example.interim.ui.home.employer_view.EmployerViewPageAdapter
import com.example.interim.ui.home.user_view.WorkerViewPageAdapter
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    private lateinit var adapter: FragmentStateAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val user_id = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getLong("user_id", -1)!!
        val user_role = activity?.getSharedPreferences("interim", Context.MODE_PRIVATE)?.getString("user_role", "worker")!!


        val view = inflater.inflate(R.layout.fragment_home_tabs, container, false)

        tabLayout = view.findViewById(R.id.home_tabs)
        viewPager = view.findViewById(R.id.home_view_pager)

        when(user_role){
            "worker" -> {
                adapter = WorkerViewPageAdapter(fragment = this)
            }
            "employer" -> {
                adapter = EmployerViewPageAdapter(fragment = this)
            }
            "admin" -> {
                val tab = tabLayout.getTabAt(0)
                tab?.text = "Demandes de création de compte"

                val tab2 = tabLayout.getTabAt(1)
                tab2?.text = "Signalements"


                adapter = AdminViewPageAdapter(fragment = this)
            }
        }

        viewPager.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            }
        )

        return view

    }

}