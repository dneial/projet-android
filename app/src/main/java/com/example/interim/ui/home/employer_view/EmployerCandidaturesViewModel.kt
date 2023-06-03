package com.example.interim.ui.home.employer_view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.services.CandidatureService
import com.example.interim.models.Candidature

class EmployerCandidaturesViewModel(var user_id: Long): ViewModel() {


    var candidatureService: CandidatureService = CandidatureService()

    private val _candidatures = MutableLiveData<List<Candidature>>().apply {
        value = candidatureService.readAllByEmployer(user_id)
    }


    val candidatures: LiveData<List<Candidature>> = _candidatures

    fun refresh() {
        _candidatures.value = candidatureService.readAllByEmployer(user_id)
    }


}