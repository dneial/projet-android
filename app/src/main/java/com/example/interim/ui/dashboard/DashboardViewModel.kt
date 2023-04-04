package com.example.interim.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.models.Offre
import com.example.interim.models.OffreList

class DashboardViewModel : ViewModel() {

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = OffreList().getOffreList()
    }

    val offres: LiveData<List<Offre>> = _offres
}