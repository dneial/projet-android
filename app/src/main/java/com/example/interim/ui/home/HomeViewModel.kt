package com.example.interim.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.CandidatureService
import com.example.interim.database.OffreService
import com.example.interim.models.Candidature
import com.example.interim.models.Offre

class HomeViewModel(var user_id: Long) : ViewModel() {


    var candidatureService: CandidatureService = CandidatureService()

    private val _candidatures = MutableLiveData<List<Candidature.CandidatureView>>().apply {
        value = candidatureService.readAll(user_id)
    }


    val candidatures: LiveData<List<Candidature.CandidatureView>> = _candidatures

    fun refresh() {
        _candidatures.value = candidatureService.readAll(user_id)
    }

}