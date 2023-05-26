package com.example.interim.models

import android.content.ContentValues
import com.example.interim.database.Requetes

class Candidature (
    var id: Long,
    var offre: Offre,
    var user: TemporaryWorker,
    var status: String,
    var date: String
    ){


    override fun toString(): String {
        return "Candidature(id='$id', " +
                "id_offre='${offre.id}'," +
                "id_candidat='${user.getId()}', " +
                "status=$status, " +
                "date='$date')"
    }

    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(Requetes.COL_ID_OFFRE_CANDIDATURE, offre.id)
        values.put(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE, user.getId())
        values.put(Requetes.COL_STATUS_CANDIDATURE, status)
        values.put(Requetes.COL_DATE_CANDIDATURE, date)
        return values
    }

}