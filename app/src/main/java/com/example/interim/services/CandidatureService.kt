package com.example.interim.services

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Candidature
import com.example.interim.models.Offre

class CandidatureService {

    var db: SQLiteDatabase = DataBase.db

    fun create(candidature: Candidature){
        val values = candidature.toContentValues()
        // check if id - offre pair already exists

        if(!check_if_candidature_exists(candidature)){
            val inserted_id =
                db.insert(Requetes.TABLE_CANDIDATURE, null, values)

            candidature.id = inserted_id
        }

    }

    private fun check_if_candidature_exists(candidature: Candidature): Boolean {
        val selection =
            "${Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE} = ? AND ${Requetes.COL_ID_OFFRE_CANDIDATURE} = ?"
        val selectionArgs =
            arrayOf(candidature.user.getId().toString(), candidature.offre.id.toString())
        val cursor =
            db.query(Requetes.TABLE_CANDIDATURE, null, selection, selectionArgs, null, null, null)
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists

    }

    fun readAll(): ArrayList<Candidature> {
        val candidatures = ArrayList<Candidature>()
        val cursor = db.rawQuery(Requetes.SELECT_ALL_CANDIDATURE, null)

        if (cursor.moveToFirst()) {
            do {
                val offre = OffreService.cursorToOffre(cursor)
                val candidature = cursorToCandidature(cursor, offre)
                candidatures.add(candidature)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return candidatures
    }

    @SuppressLint("Range")
    fun readAllByUser(user_id: Long): ArrayList<Candidature> {
        val candidatures = ArrayList<Candidature>()
        val cursor = db.rawQuery(Requetes.SELECT_CANDIDATURE_BY_ID_TEMPORARYWORKER, arrayOf(user_id.toString()))

        if (cursor.moveToFirst()) {
            do {
                val offre = OffreService.cursorToOffre(cursor)
                val candidature = cursorToCandidature(cursor, offre)
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
            offre = OffreService.cursorToOffre(cursor)
            candidature = cursorToCandidature(cursor, offre)
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
        var offre: Offre? = null

        if(cursor.moveToFirst()){
            offre = OffreService.cursorToOffre(cursor)
            val candidature = cursorToCandidature(cursor, offre)
            candidatures.add(candidature)
        }
        cursor.close()
        return candidatures
    }

    private fun cursorToCandidature(cursor: Cursor, offre: Offre): Candidature {
        val userService: UsersService = UsersService()
        val user_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER_CANDIDATURE))
        val user = userService.getTemporaryWorker(user_id)!!
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_CANDIDATURE))
        val date = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CANDIDATURE))
        val status = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_STATUS_CANDIDATURE))

        return Candidature(
            id=id,
            offre=offre,
            user=user,
            date=date,
            status=status
        )
    }


    fun accept(candidature: Candidature) {
        val selection = "${Requetes.COL_ID_CANDIDATURE} = ?"
        val selectionArgs = arrayOf(candidature.id.toString())
        val values = ContentValues()
        values.put(Requetes.COL_STATUS_CANDIDATURE, "Acceptée")
        db.update(Requetes.TABLE_CANDIDATURE, values, selection, selectionArgs)
        candidature.status = "Acceptée"
    }

    fun refuse(candidature: Candidature) {
        val selection = "${Requetes.COL_ID_CANDIDATURE} = ?"
        val selectionArgs = arrayOf(candidature.id.toString())
        val values = ContentValues()
        values.put(Requetes.COL_STATUS_CANDIDATURE, "Refusée")
        db.update(Requetes.TABLE_CANDIDATURE, values, selection, selectionArgs)
        candidature.status = "Refusée"

    }

}