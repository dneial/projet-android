package com.example.interim.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.CandidatureService
import com.example.interim.models.Candidature

class CandidaturesViewModel(var user_id: Long) : ViewModel() {


    var candidatureService: CandidatureService = CandidatureService()

    private val _candidatures = MutableLiveData<List<Candidature.CandidatureUserView>>().apply {
        value = candidatureService.readAllByUser(user_id)
    }


    val candidatures: LiveData<List<Candidature.CandidatureUserView>> = _candidatures

    fun refresh() {
        _candidatures.value = candidatureService.readAllByUser(user_id)
    }

}