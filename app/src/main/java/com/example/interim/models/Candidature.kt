package com.example.interim.models

import android.content.ContentValues

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
        values.put("id_offre", id_offre)
        values.put("id_user", id_candidat)
        values.put("status", status)
        values.put("date", date)
        return values
    }

    data class CandidatureView (
        val id: Long,
        val offre_titre: String,
        val date: String,
        val status: String
    ) {

    }
}