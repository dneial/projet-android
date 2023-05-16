package com.example.interim.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.User

class UsersService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(user: User){
        Log.d("create", "create: $user")
        val query = "INSERT INTO ${Requetes.TABLE_USERS} (" +
                "${Requetes.COL_LASTNAME_USER}, " +
                "${Requetes.COL_NAME_USER}, " +
                "${Requetes.COL_EMAIL_USER}, " +
                "${Requetes.COL_PASSWORD_USER}, " +
                "${Requetes.COL_PHONE_USER}, " +
                "${Requetes.COL_ROLE_USER}" +
                ") VALUES (" +
                "'${user.firstName}', " +
                "'${user.lastName}', " +
                "'${user.email}', " +
                "'${user.password}', " +
                "'${user.phone}', " +
                "'${user.role}'" +
                ");";

        db.execSQL(query)
    }

    @SuppressLint("Range")
    fun signIn(email: String, password: String): User?{
        val query = "SELECT * FROM ${Requetes.TABLE_USERS} WHERE ${Requetes.COL_EMAIL_USER} = '$email' AND ${Requetes.COL_PASSWORD_USER} = '$password';"
        val cursor = db.rawQuery(query, null)
        var user: User? = null

        if(cursor.moveToFirst()){
            user = User(
                cursor.getLong(cursor.getColumnIndex(Requetes.COL_ID_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_NAME_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_LASTNAME_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_EMAIL_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PASSWORD_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_PHONE_USER)),
                cursor.getString(cursor.getColumnIndex(Requetes.COL_ROLE_USER))
            )
            cursor.close()
        }

        return user

    }



}