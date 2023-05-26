package com.example.interim.services

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker

class UsersService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(temporaryWorker: TemporaryWorker){
        Log.d("create", "create: $temporaryWorker")
        val query = "INSERT INTO ${Requetes.TABLE_TEMPORARYWORKERS} (" +
                "${Requetes.COL_LASTNAME_TEMPORARYWORKER}, " +
                "${Requetes.COL_NAME_TEMPORARYWORKER}, " +
                "${Requetes.COL_EMAIL_TEMPORARYWORKER}, " +
                "${Requetes.COL_PASSWORD_TEMPORARYWORKER}, " +
                "${Requetes.COL_PHONE_TEMPORARYWORKER}, " +
                "${Requetes.COL_BIRTHDAY_TEMPORARYWORKER}, " +
                "${Requetes.COL_NATIONALITY_TEMPORARYWORKER}," +
                "${Requetes.COL_CITY_TEMPORARYWORKER}," +
                "${Requetes.COL_COMMENTARY_TEMPORARYWORKER}" +
                ") VALUES (" +
                "'${temporaryWorker.getLastName()}', " +
                "'${temporaryWorker.getFirstName()}', " +
                "'${temporaryWorker.getEmail()}', " +
                "'${temporaryWorker.getPassword()}', " +
                "'${temporaryWorker.getPhone()}', " +
                "'${temporaryWorker.getBirthday()}'" +
                "'${temporaryWorker.getNationality()}'" +
                "'${temporaryWorker.getCity()}'" +
                "'${temporaryWorker.getCommentary()}'" +
                ");";

        db.execSQL(query)
    }

    fun create(employer: Employer){
        Log.d("create", "create: $employer")
        val query = "INSERT INTO ${Requetes.TABLE_EMLPLOYERS} (" +
                "${Requetes.COL_NAME_EMLPLOYER}, " +
                "${Requetes.COL_SERVICE_EMLPLOYER}, " +
                "${Requetes.COL_SUBSERVICE_EMLPLOYER}, " +
                "${Requetes.COL_SIRET_EMLPLOYER}, " +
                "${Requetes.COL_CONTACT_EMLPLOYER}, " +
                "${Requetes.COL_SUBCONTACT_EMLPLOYER}, " +
                "${Requetes.COL_EMAIL_EMLPLOYER}," +
                "${Requetes.COL_SUBEMAIL_EMLPLOYER}," +
                "${Requetes.COL_PHONE_EMLPLOYER}" +
                "${Requetes.COL_SUBPHONE_EMLPLOYER}," +
                "${Requetes.COL_ADDRESS_EMLPLOYER}," +
                "${Requetes.COL_PASSWORD_EMLPLOYER}" +
                "${Requetes.COL_COMMENTARY_EMLPLOYER}" +
                ") VALUES (" +
                "'${employer.getName()}', " +
                "'${employer.getService()}', " +
                "'${employer.getSubService()}', " +
                "'${employer.getSIRET()}', " +
                "'${employer.getContact()}', " +
                "'${employer.getSubContact()}'" +
                "'${employer.getEmail()}'" +
                "'${employer.getSubEmail()}'" +
                "'${employer.getPhone()}'" +
                "'${employer.getSubPhone()}'" +
                "'${employer.getAddress()}'" +
                "'${employer.getPassword()}'" +
                "'${employer.getCommentary()}'" +
                ");";

        db.execSQL(query)
    }

    @SuppressLint("Range")
    fun signIn(email: String, password: String): TemporaryWorker? {
        val query = "SELECT * FROM ${Requetes.TABLE_TEMPORARYWORKERS} WHERE " +
                "${Requetes.COL_EMAIL_TEMPORARYWORKER} = '$email' AND ${Requetes.COL_PASSWORD_TEMPORARYWORKER} = '$password';"

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
            )
            cursor.close()
        }

        return temporaryWorker

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

        return temporaryWorker
    }


    fun readAll(): ArrayList<TemporaryWorker> {
        val query = Requetes.SELECT_ALL_TEMPORARYWORKERS;
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


}