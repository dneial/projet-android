package com.example.interim.models

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import com.example.interim.database.Requetes

class Offre(
    var title: String?,
    var metier: String?,
    var description: String?,
    var date_debut: String?,
    var date_fin: String?,
    var remuneration: String?,
    var id: Long,
    var ville: String? = "",
    var employer: Employer
) {


    override fun toString(): String {
        return "Offre(name='$title', " +
                "metier='$metier'," +
                "description='$description', " +
                "date_debut=$date_debut," +
                "date_fin=$date_fin, " +
                "remuneration='$remuneration')"
    }


    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(Requetes.COL_TITLE, title)
        values.put(Requetes.COL_METIER, metier)
        values.put(Requetes.COL_DESCRIPTION, description)
        values.put(Requetes.COL_DATE_DEBUT, date_debut)
        values.put(Requetes.COL_DATE_FIN, date_fin)
        values.put(Requetes.COL_REMUNERATION, remuneration)
        values.put(Requetes.COL_CITY, ville)
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, employer?.getId())
        return values
    }


}