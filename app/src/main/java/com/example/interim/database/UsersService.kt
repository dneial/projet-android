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
    fun signIn(email: String, password: String): User? {
        val query = "SELECT * FROM ${Requetes.TABLE_USERS} WHERE " +
                "${Requetes.COL_EMAIL_USER} = '$email' AND ${Requetes.COL_PASSWORD_USER} = '$password';"

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

    @SuppressLint("Range")
    fun getRole(user_id: Long): String {
        val query = "SELECT ${Requetes.COL_ROLE_USER} FROM ${Requetes.TABLE_USERS} WHERE ${Requetes.COL_ID_USER} = '$user_id';"
        val cursor = db.rawQuery(query, null)
        var role: String? = null

        if(cursor.moveToFirst()){
            role = cursor.getString(cursor.getColumnIndex(Requetes.COL_ROLE_USER))
            cursor.close()
        }

        return role!!
    }

    fun readAll(): ArrayList<User> {
        val query = Requetes.SELECT_ALL_USERS;
        val sortOrder = "${Requetes.COL_ID_USER} DESC"
        val cursor = db.query(Requetes.TABLE_USERS, null, null, null, null, null, sortOrder);
        val users = ArrayList<User>();

        while (cursor.moveToNext()) {
            val user = User(
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_LASTNAME_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_USER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ROLE_USER))
            );
            users.add(user);
        }
        cursor.close();

        return users;
    }

}