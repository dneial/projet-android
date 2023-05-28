package com.example.interim.ui.home.employer_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.services.OffreService
import com.example.interim.models.Offre

class OffresPublieesViewModel (var user_id: Long) : ViewModel() {

    var offreService: OffreService = OffreService()

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = offreService.getPublieesByEmployer(user_id)
    }


    val offres: LiveData<List<Offre>> = _offres

    fun refresh() {
        _offres.value = offreService.getPublieesByEmployer(user_id)
    }
}
