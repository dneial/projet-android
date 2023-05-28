package com.example.interim.ui.dashboard

import android.content.ContentValues
import android.util.Log
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

    fun filterByCity(ville: String) {
        if(ville == "")
            _offres.value = offreService.readAll()
        else
            _offres.value = offreService.filter(ville)
    }

    fun filterByText(text: String) {
        _offres.value = offreService.readAll().filter { offre ->
            offre.title!!.contains(text, true) ||
            offre.description!!.contains(text, true) ||
            offre.metier!!.contains(text, true)
        }

    }

    fun filterByValues(values: ContentValues) {

        _offres.value = offreService.filter(values)
    }


}