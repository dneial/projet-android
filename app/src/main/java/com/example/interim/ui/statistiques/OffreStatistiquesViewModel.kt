package com.example.interim.ui.statistiques

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.models.Offre
import com.example.interim.services.OffreService

class OffreStatistiquesViewModel: ViewModel() {

    var offreService: OffreService = OffreService()

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = offreService.readAll()
    }

    val offres: LiveData<List<Offre>> = _offres

}