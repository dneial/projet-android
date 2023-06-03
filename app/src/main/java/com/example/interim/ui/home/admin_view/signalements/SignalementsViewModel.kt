package com.example.interim.ui.home.admin_view.signalements

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.models.Report
import com.example.interim.services.ReportsService

class SignalementsViewModel: ViewModel() {

    var reportsService: ReportsService = ReportsService()

    private val _reports = MutableLiveData<List<Report>>().apply {
        value = reportsService.readAll()
    }

    val reports: LiveData<List<Report>> = _reports
}