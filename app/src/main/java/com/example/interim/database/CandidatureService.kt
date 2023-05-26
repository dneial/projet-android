package com.example.interim.database

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Candidature
import com.example.interim.models.Offre
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User

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
    fun readAllByUser(user_id: Long): ArrayList<Candidature> {
        val candidatures = ArrayList<Candidature>()

        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_BY_ID_TEMPORARYWORKER, arrayOf(user_id.toString()))

        if (cursor.moveToFirst()) {
            do {
                val offre = Offre(
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
                )
                val user = UsersService().getTemporaryWorker(user_id)

                val candidature = Candidature(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                    offre,
                    user!!,
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE))
                )
                candidatures.add(candidature)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return candidatures
    }

    fun getCandidature(candidature_id: Long): Candidature {
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
            val user = UsersService().getTemporaryWorker(cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER)))

            candidature = Candidature(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_CANDIDATURE)),
                offre,
                user!!,
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE))
                )
        }
        cursor.close()
        return candidature!!
    }
}