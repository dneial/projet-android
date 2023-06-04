package com.example.interim.models

import android.content.ContentValues
import com.example.interim.database.Requetes

class Report(
    var id: Long,
    var email: String,
    var offre: Offre,
    var comment: String
) {
    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(Requetes.COL_EMAIL_REPORT, email)
        values.put(Requetes.COL_ID_OFFRE, offre.id)
        values.put(Requetes.COL_COMMENTARY_REPORT, comment)

        return values
    }

    override fun toString(): String {
        return "Report(id=$id, email='$email', Offre=$offre, comment='$comment')"
    }



}