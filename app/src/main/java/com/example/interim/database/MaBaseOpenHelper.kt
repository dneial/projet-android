package com.example.interim.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Debug
import android.util.Log

object DataBase {
    lateinit var db: SQLiteDatabase
    fun init(context: Context) {
        val helper = MaBaseOpenHelper(context)
        db = helper.writableDatabase
    }
}

class MaBaseOpenHelper(context: Context, name: String = "DB", version: Int = 1) : SQLiteOpenHelper(
    context,
    name,
null,
    version
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(Requetes.CREATE_TABLE_OFFRE)
        db?.execSQL(Requetes.CREATE_TABLE_USERS)
        db?.execSQL(Requetes.CREATE_TABLE_CANDIDATURE)

        create_default_offres(db)
        create_default_user(db)
    }

    private fun create_default_offres(db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put(Requetes.COL_TITLE, "Java Developer")
        values.put(Requetes.COL_METIER, "IT")
        values.put(Requetes.COL_DESCRIPTION, "Looking for an experienced Java developer to join our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-06-01")
        values.put(Requetes.COL_DATE_FIN, "2023-12-31")
        values.put(Requetes.COL_REMUNERATION, 50000)
        values.put(Requetes.COL_VILLE, "Montpellier")
        db?.insert(Requetes.TABLE_OFFRE, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Marketing Manager")
        values.put(Requetes.COL_METIER, "Marketing")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced marketing manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-06-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_VILLE, "Montpellier")
        db?.insert(Requetes.TABLE_OFFRE, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Sales Manager")
        values.put(Requetes.COL_METIER, "Sales")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced sales manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-06-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_VILLE, "Paris")
        db?.insert(Requetes.TABLE_OFFRE, null, values)
    }

    private fun create_default_user(db: SQLiteDatabase?){
        val values = ContentValues()
        values.put(Requetes.COL_NAME_USER, "admin")
        values.put(Requetes.COL_LASTNAME_USER, "admin")
        values.put(Requetes.COL_EMAIL_USER, "admin@admin.com")
        values.put(Requetes.COL_PASSWORD_USER, "admin")
        values.put(Requetes.COL_PHONE_USER, "0000000000")
        values.put(Requetes.COL_ROLE_USER, "admin")
        values.put(Requetes.COL_VILLE_USER, "Montpellier")
        values.put(Requetes.COL_NATIONALITY_USER, "France")
        values.put(Requetes.COL_BIRTHDAY_USER, "2000-01-01")
        db?.insert(Requetes.TABLE_USERS, null, values)

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Requetes.DROP_TABLE_OFFRE);
        db?.execSQL(Requetes.DROP_TABLE_USERS);
        db?.execSQL(Requetes.DROP_TABLE_CANDIDATURE);
        onCreate(db);
    }
}