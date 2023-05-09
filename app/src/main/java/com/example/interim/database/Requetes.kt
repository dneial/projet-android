package com.example.interim.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Offre

object Requetes {

    const val TABLE_OFFRE: String = "offres";
    const val COL_ID: String = "id";
    const val COL_NAME: String = "title";
    const val COL_METIER: String = "metier";
    const val COL_DESCRIPTION: String = "description";
    const val COL_DATE_DEBUT: String = "date_debut";
    const val COL_DATE_FIN: String = "date_fin";
    const val COL_REMUNERATION: String = "remuneration";
    const val COL_ID_ENTREPRISE: String = "id_entreprise";
    const val CREATE_TABLE_OFFRE: String = "CREATE TABLE $TABLE_OFFRE (" +
            "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME TEXT," +
            "$COL_METIER TEXT," +
            "$COL_DESCRIPTION TEXT," +
            "$COL_DATE_DEBUT DATE," +
            "$COL_DATE_FIN DATE," +
            "$COL_REMUNERATION TEXT," +
            "$COL_ID_ENTREPRISE INTEGER" +
            ");";

    const val DROP_TABLE_OFFRE: String = "DROP TABLE IF EXISTS $TABLE_OFFRE;";
    const val SELECT_ALL_OFFRE: String = "SELECT * FROM $TABLE_OFFRE;";


    const val TABLE_USERS: String = "users";
    const val COL_ID_USER: String = "id";
    const val COL_NAME_USER: String = "name";
    const val COL_LASTNAME_USER: String = "lastname";
    const val COL_EMAIL_USER: String = "email";
    const val COL_PASSWORD_USER: String = "password";
    const val COL_PHONE_USER: String = "phone";
    const val COL_ROLE_USER: String = "role";
    const val COL_VILLE_USER: String = "ville";
    const val COL_VILLE_BIRTHDAY: String = "birthday";

    const val CREATE_TABLE_USERS: String = "CREATE TABLE $TABLE_USERS (" +
            "$COL_ID_USER INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_USER TEXT," +
            "$COL_LASTNAME_USER TEXT," +
            "$COL_EMAIL_USER TEXT," +
            "$COL_PASSWORD_USER TEXT," +
            "$COL_PHONE_USER TEXT," +
            "$COL_ROLE_USER TEXT" +
            "$COL_VILLE_USER TEXT" +
            "$COL_VILLE_BIRTHDAY DATE" +
            ");";

    const val DROP_TABLE_USERS: String = "DROP TABLE IF EXISTS $TABLE_USERS;";
    const val SELECT_ALL_USERS: String = "SELECT * FROM $TABLE_USERS;";


}


