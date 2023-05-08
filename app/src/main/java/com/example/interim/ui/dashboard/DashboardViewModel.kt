package com.example.interim.ui.dashboard

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.database.MaBaseOpenHelper
import com.example.interim.database.OffreService
import com.example.interim.models.Offre
import com.example.interim.models.OffreList

class DashboardViewModel(var context: Context) : ViewModel() {

    var offreService: OffreService = OffreService(context)

    private val _offres = MutableLiveData<List<Offre>>().apply {
        value = offreService.readAll()
    }


    val offres: LiveData<List<Offre>> = _offres

    fun refresh() {
        _offres.value = offreService.readAll()
    }
}