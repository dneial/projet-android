package com.example.interim.database

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Candidature
import com.example.interim.models.Offre
import com.example.interim.services.UsersService

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
    fun readAllByUser(user_id: Long): ArrayList<Candidature> {
        val candidatures = ArrayList<Candidature>()
        val userService: UsersService = UsersService()
        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_BY_ID_TEMPORARYWORKER, arrayOf(user_id.toString()))

        if (cursor.moveToFirst()) {
            do {
                val emp_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
                val employer = userService.getEmployer(emp_id)
                val offre = Offre(
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                    employer!!
                )
                val user = userService.getTemporaryWorker(user_id)

                val candidature = Candidature(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_CANDIDATURE)),
                    offre,
                    user!!,
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE))
                )
                Log.d("candidature", candidature.id.toString())
                candidatures.add(candidature)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return candidatures
    }

    fun getCandidature(candidature_id: Long): Candidature {
        val selectionArgs = arrayOf(candidature_id.toString())
        val cursor = db.rawQuery(Requetes.GET_CANDIDATURE_BY_ID, selectionArgs)
        var offre: Offre? = null
        var candidature: Candidature? = null
        val userService: UsersService = UsersService()

        if (cursor.moveToFirst()) {
            val emp_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = userService.getEmployer(emp_id)
            offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
            )
            val user = userService.getTemporaryWorker(cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE)))

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

    fun delete(candidatureId: Long) {
        val selection = "${Requetes.COL_ID_CANDIDATURE} = ?"
        val selectionArgs = arrayOf(candidatureId.toString())
        db.delete(Requetes.TABLE_CANDIDATURE, selection, selectionArgs)
    }

    fun readAllByEmployer(employerId: Long): List<Candidature>? {
        val candidatures = ArrayList<Candidature>()
        val selectionArgs = arrayOf(employerId.toString())
        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_BY_ID_EMPLOYER, selectionArgs)
        val userService: UsersService = UsersService()
        var offre: Offre? = null

        if(cursor.moveToFirst()){
            val emp_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = userService.getEmployer(emp_id)
            offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
            )
            val user = userService.getTemporaryWorker(cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE)))

            val candidature = Candidature(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_CANDIDATURE)),
                offre,
                user!!,
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE))
            )
            candidatures.add(candidature)
        }
        cursor.close()
        return candidatures
    }
}