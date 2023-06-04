package com.example.interim.ui.home.admin_view.signalements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.interim.models.Report
import com.example.interim.services.ReportService

class SignalementsViewModel: ViewModel() {


    var reportService: ReportService = ReportService()

    private val _reports = MutableLiveData<List<Report>>().apply {
        value = reportService.readAll()
    }

    val reports: LiveData<List<Report>> = _reports

    fun deleteReport(item: Report) {
        reportService.delete(item.id)
        _reports.value = reportService.readAll()
    }
}