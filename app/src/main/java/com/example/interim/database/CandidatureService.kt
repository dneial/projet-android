package com.example.interim.database

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
}