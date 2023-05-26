package com.example.interim.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Candidature
import com.example.interim.models.Offre

class CandidatureService {
    var db: SQLiteDatabase = DataBase.db

    public fun create(candidature: Candidature): Candidature{
        val values = candidature.toContentValues()

        val inserted_id =
            db.insert(Requetes.TABLE_CANDIDATURE, null, values)

        Log.d("inserted_id", inserted_id.toString())
        candidature.id = inserted_id

        return candidature
    }

    @SuppressLint("Range")
    fun readAllByUser(user_id: Long): ArrayList<Candidature.CandidatureUserView> {
        val candidatures = ArrayList<Candidature.CandidatureUserView>()

        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_DATE_AND_STATUS_AND_OFFRE_TITLE_BY_ID_TEMPORARYWORKER, arrayOf(user_id.toString()))

        if (cursor.moveToFirst()) {
            do {
                val candidature = Candidature.CandidatureUserView(
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

    fun getOffreByCandidatureId(candidature_id: Long): Pair<Offre, Candidature> {
        val selection = "${Requetes.COL_ID_CANDIDATURE} = ?"
        val selectionArgs = arrayOf(candidature_id.toString())
        val cursor = db.rawQuery(Requetes.GET_OFFRE_BY_CANDIDATURE_ID, selectionArgs)
        var offre: Offre? = null
        var candidature: Candidature? = null
        if (cursor.moveToFirst()) {
            offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
            )
            candidature = Candidature(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_CANDIDATURE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_CANDIDATURE)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE))

                )
        }
        cursor.close()
        return Pair(offre!!, candidature!!)
    }
}