package com.example.interim.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.OffreService
import com.example.interim.models.Offre

class DashboardViewModel() : ViewModel() {

    var offreService: OffreService = OffreService()

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = offreService.readAll()
    }


    val offres: LiveData<List<Offre>> = _offres

    fun refresh() {
        _offres.value = offreService.readAll()
    }

    fun filter(ville: String) {
        // TODO: filtre par ville
        if(ville != "Montpellier") _offres.value = offreService.readAll()
        else _offres.value = offreService.filter(ville)
    }
}