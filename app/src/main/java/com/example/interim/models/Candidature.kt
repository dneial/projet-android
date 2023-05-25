package com.example.interim.models

import android.content.ContentValues
import com.example.interim.database.Requetes

class Candidature (
    var id: Long,
    var id_offre: Long,
    var id_candidat: Long,
    var status: String,
    var date: String
    ){


    override fun toString(): String {
        return "Candidature(id='$id', " +
                "id_offre='$id_offre'," +
                "id_candidat='$id_candidat', " +
                "status=$status, " +
                "date='$date')"
    }

    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(Requetes.COL_ID_OFFRE_CANDIDATURE, id_offre)
        values.put(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE, id_candidat)
        values.put(Requetes.COL_STATUS_CANDIDATURE, status)
        values.put(Requetes.COL_DATE_CANDIDATURE, date)
        return values
    }

    data class CandidatureUserView (
        val id: Long,
        val offre_titre: String,
        val date: String,
        val status: String
    )

    data class CandidatureEmployerView (
        val id: Long,
        val candidat_name: String,
        val date: String,
        val status: String
        )
}