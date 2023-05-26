package com.example.interim.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.CandidatureService
import com.example.interim.models.Candidature

class CandidaturesViewModel(var user_id: Long) : ViewModel() {


    var candidatureService: CandidatureService = CandidatureService()

    private val _candidatures = MutableLiveData<List<Candidature>>().apply {
        value = candidatureService.readAllByUser(user_id)
    }


    val candidatures: LiveData<List<Candidature>> = _candidatures

    fun refresh() {
        _candidatures.value = candidatureService.readAllByUser(user_id)
    }

    fun filterByText(text: String) {
        _candidatures.value = candidatureService.readAllByUser(user_id).filter { candidature ->
                    candidature.offre.title!!.contains(text, true) ||
                    candidature.offre.description!!.contains(text, true) ||
                    candidature.offre.metier!!.contains(text, true)
        }
    }

}