package com.example.interim.services

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Offre
import com.example.interim.models.Report

class ReportService {

    var db: SQLiteDatabase = DataBase.db

    fun create(report: Report) {
        val values = report.toContentValues()
        val id = db.insert(Requetes.TABLE_REPORTS, null, values)
        report.id = id
    }

    fun readAll(): List<Report> {
        val cursor = db.rawQuery(Requetes.SELECT_ALL_REPORTS, null)
        val reports = ArrayList<Report>()

        while (cursor.moveToNext()) {
            val offre = OffreService.cursorToOffre(cursor)
            val report = cursorToReport(cursor, offre)
            Log.d("ReportsService", "report: $report")
            reports.add(report)
        }

        cursor.close()
        Log.d("ReportsService", "reports: $reports")
        return reports

    }

    @SuppressLint("Range")
    private fun cursorToReport(cursor: Cursor, offre: Offre): Report {
        val id = cursor.getLong(cursor.getColumnIndex(Requetes.COL_ID_REPORT))
        val email = cursor.getString(cursor.getColumnIndex(Requetes.COL_EMAIL_REPORT))
        val comment = cursor.getString(cursor.getColumnIndex(Requetes.COL_COMMENTARY_REPORT))

        return Report(id=id, email=email, offre=offre, comment=comment)
    }

    fun delete(id: Long) {
        val selection = "${Requetes.COL_ID_REPORT} = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(Requetes.TABLE_REPORTS, selection, selectionArgs)
    }
}
