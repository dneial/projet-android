package com.example.interim.services

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User

class UsersService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(temporaryWorker: TemporaryWorker){
        Log.d("create", "create: $temporaryWorker")
        val values = temporaryWorker.toContentValues()
        temporaryWorker.setId(db.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values))
    }

    fun create(employer: Employer){
        Log.d("create", "create: $employer")
        val values = employer.toContentValues()
        Employer(db.insert(Requetes.TABLE_EMPLOYER, null, values))
    }

    @SuppressLint("Range")
    fun signIn(email: String, password: String): User? {
        var query = "SELECT * " +
                "FROM ${Requetes.TABLE_TEMPORARYWORKERS} "+
                " WHERE ${Requetes.COL_EMAIL_TEMPORARYWORKER} = ? AND " +
                "${Requetes.COL_PASSWORD_TEMPORARYWORKER} = ?"

        val selectionArgs = arrayOf(email, password)
        var cursor = db.rawQuery(query, selectionArgs)
        var user : User? = null

        if(cursor.moveToFirst()){
            user = TemporaryWorker(
                cursor.getLong(cursor.getColumnIndex(Requetes.COL_ID_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_NAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_LASTNAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_EMAIL_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PASSWORD_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PHONE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_NATIONALITY_TEMPORARYWORKER))
            )
            cursor.close()
        }else{
            query = "SELECT * " +
                    "FROM ${Requetes.TABLE_EMPLOYER} "+
                    " WHERE ${Requetes.COL_EMAIL_EMPLOYER} = ? AND " +
                    "${Requetes.COL_PASSWORD_EMPLOYER} = ?"

            cursor = db.rawQuery(query, selectionArgs)
            if(cursor.moveToFirst()){
                user = Employer(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_EMPLOYER )),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SERVICE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBSERVICE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SIRET_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CONTACT_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBCONTACT_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBEMAIL_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBPHONE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ADDRESS_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_COMMENTARY_EMPLOYER))
                )
                cursor.close()
            }
        }
        return user
    }

    @SuppressLint("Range")
    fun getTemporaryWorker(TEMPORARYWORKER_id: Long): TemporaryWorker? {
        val query = "SELECT * FROM ${Requetes.TABLE_TEMPORARYWORKERS} WHERE ${Requetes.COL_ID_TEMPORARYWORKER} = '$TEMPORARYWORKER_id';"
        val cursor = db.rawQuery(query, null)
        var temporaryWorker: TemporaryWorker? = null

        if(cursor.moveToFirst()){
            temporaryWorker = TemporaryWorker(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_LASTNAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NATIONALITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_COMMENTARY_TEMPORARYWORKER))
            );
        }
        cursor.close()
        return temporaryWorker
    }


    fun readAll(): ArrayList<TemporaryWorker> {
        val sortOrder = "${Requetes.COL_ID_TEMPORARYWORKER} DESC"
        val cursor = db.query(Requetes.TABLE_TEMPORARYWORKERS, null, null, null, null, null, sortOrder);
        val temporaryWorkers = ArrayList<TemporaryWorker>();

        while (cursor.moveToNext()) {
            val temporaryWorker = TemporaryWorker(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_LASTNAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NATIONALITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_COMMENTARY_TEMPORARYWORKER))
            );
            temporaryWorkers.add(temporaryWorker);
        }
        cursor.close();

        return temporaryWorkers;
    }

    fun getEmployer(employerId: Long): Employer? {
        val query = "SELECT * FROM ${Requetes.TABLE_EMPLOYER} WHERE ${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(employerId.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        var employer: Employer? = null

        if(cursor.moveToFirst()){
            employer = Employer(
                    cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_EMPLOYER )),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SERVICE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBSERVICE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SIRET_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CONTACT_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBCONTACT_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBEMAIL_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBPHONE_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ADDRESS_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_EMPLOYER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_COMMENTARY_EMPLOYER))
            );
        }
        cursor.close()
        return employer
    }



}
