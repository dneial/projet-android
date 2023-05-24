package com.example.interim.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interim.ui.dashboard.DashboardViewModel

class HomeViewModelFactory(private val user_id: Long) : ViewModelProvider.Factory{


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(user_id) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }


}