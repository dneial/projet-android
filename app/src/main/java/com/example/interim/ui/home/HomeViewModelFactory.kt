package com.example.interim.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interim.ui.home.employer_view.EmployerCandidaturesViewModel
import com.example.interim.ui.home.employer_view.OffresPublieesViewModel
import com.example.interim.ui.home.user_view.OffresEnregistreesViewModel
import com.example.interim.ui.home.user_view.UserCandidaturesViewModel

class HomeViewModelFactory(private val user_id: Long, private val user_role: String) : ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            Log.d("HomeViewModelFactory", modelClass.toString())

            if (modelClass.isAssignableFrom(UserCandidaturesViewModel::class.java)) {
                    return UserCandidaturesViewModel(user_id) as T
            }
            else if (modelClass.isAssignableFrom(EmployerCandidaturesViewModel::class.java)) {
                return EmployerCandidaturesViewModel(user_id) as T
            }
            else if (modelClass.isAssignableFrom(OffresEnregistreesViewModel::class.java)) {
                return OffresEnregistreesViewModel(user_id) as T
            }
            else if (modelClass.isAssignableFrom(OffresPublieesViewModel::class.java)) {
                return OffresPublieesViewModel(user_id) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }


}