package com.example.interim.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.example.interim.models.Candidature

class CandidatureService {
    var db: SQLiteDatabase = DataBase.db

    public fun create(candidature: Candidature): Candidature{
        val values = candidature.toContentValues()

        val inserted_id =
            db.insert(Requetes.TABLE_CANDIDATURE, null, values)

        candidature.id = inserted_id

        return candidature
    }

    @SuppressLint("Range")
    fun readAll(user_id: Long): ArrayList<Candidature.CandidatureView> {
        val candidatures = ArrayList<Candidature.CandidatureView>()

        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_DATE_AND_STATUS_AND_OFFRE_TITLE_BY_ID_USER, arrayOf(user_id.toString()))


        if (cursor.moveToFirst()) {
            do {
                val candidature = Candidature.CandidatureView(
                    cursor.getLong(cursor.getColumnIndex(Requetes.COL_ID_CANDIDATURE)),
                    cursor.getString(cursor.getColumnIndex(Requetes.COL_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Requetes.COL_STATUS_CANDIDATURE)),
                    cursor.getString(cursor.getColumnIndex(Requetes.COL_DATE_CANDIDATURE))
                    )
                candidatures.add(candidature)
            } while (cursor.moveToNext())
        }

        cursor.close()

        return candidatures
    }
}