package com.example.interim.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.CandidatureService
import com.example.interim.database.OffreService
import com.example.interim.models.Candidature
import com.example.interim.models.Offre

class OffresEnregistreesViewModel (var user_id: Long) : ViewModel() {

    var offreService: OffreService = OffreService()

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = offreService.getEnregistresByUser(user_id)
    }


    val offres: LiveData<List<Offre>> = _offres

    fun refresh() {
        _offres.value = offreService.getEnregistresByUser(user_id)
    }
}
