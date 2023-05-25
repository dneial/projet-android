package com.example.interim.database

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.util.Log
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
                "${Requetes.COL_CITY_TEMPORARYWORKER}" +
                ") VALUES (" +
                "'${temporaryWorker.lastName}', " +
                "'${temporaryWorker.firstName}', " +
                "'${temporaryWorker.email}', " +
                "'${temporaryWorker.password}', " +
                "'${temporaryWorker.phone}', " +
                "'${temporaryWorker.birthday}'" +
                "'${temporaryWorker.nationality}'" +
                "'${temporaryWorker.city}'" +
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
                cursor.getLong(cursor.getColumnIndex(Requetes.COL_ID_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_NAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_LASTNAME_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_EMAIL_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PASSWORD_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PHONE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_ROLE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_NATIONALITY_TEMPORARYWORKER))
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
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ROLE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NATIONALITY_TEMPORARYWORKER))
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
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ROLE_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_BIRTHDAY_TEMPORARYWORKER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NATIONALITY_TEMPORARYWORKER))
            );
            temporaryWorkers.add(temporaryWorker);
        }
        cursor.close();

        return temporaryWorkers;
    }


}