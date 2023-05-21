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
    const val COL_ID_OFFRE_ENTREPRISE: String = "id_entreprise";
    const val COL_VILLE: String = "ville";

    const val CREATE_TABLE_OFFRE: String = "CREATE TABLE $TABLE_OFFRE (" +
            "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME TEXT," +
            "$COL_METIER TEXT," +
            "$COL_DESCRIPTION TEXT," +
            "$COL_DATE_DEBUT DATE," +
            "$COL_DATE_FIN DATE," +
            "$COL_REMUNERATION TEXT," +
            "$COL_VILLE TEXT," +
            "$COL_ID_OFFRE_ENTREPRISE INTEGER" +
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

    const val CREATE_TABLE_USERS: String = "CREATE TABLE $TABLE_USERS (" +
            "$COL_ID_USER INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_USER TEXT," +
            "$COL_LASTNAME_USER TEXT," +
            "$COL_EMAIL_USER TEXT," +
            "$COL_PASSWORD_USER TEXT," +
            "$COL_PHONE_USER TEXT," +
            "$COL_ROLE_USER TEXT, " +
            "$COL_VILLE_USER TEXT" +
            ");";

    const val DROP_TABLE_USERS: String = "DROP TABLE IF EXISTS $TABLE_USERS;";
    const val SELECT_ALL_USERS: String = "SELECT * FROM $TABLE_USERS;";


    const val TABLE_CANDIDATURE: String = "candidatures";
    const val COL_ID_CANDIDATURE: String = "id";
    const val COL_ID_OFFRE_CANDIDATURE: String = "id_offre";
    const val COL_ID_USER_CANDIDATURE: String = "id_user";
    const val COL_ID_ENTREPRISE_CANDIDATURE: String = "id_entreprise";
    const val COL_STATUS_CANDIDATURE: String = "status";

    const val CREATE_TABLE_CANDIDATURE: String = "CREATE TABLE $TABLE_CANDIDATURE (" +
            "$COL_ID_CANDIDATURE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_ID_OFFRE_CANDIDATURE INTEGER," +
            "$COL_ID_USER_CANDIDATURE INTEGER," +
            "$COL_ID_ENTREPRISE_CANDIDATURE INTEGER," +
            "$COL_STATUS_CANDIDATURE TEXT" +
            ");";

    const val DROP_TABLE_CANDIDATURE: String = "DROP TABLE IF EXISTS $TABLE_CANDIDATURE;";
    const val SELECT_ALL_CANDIDATURE: String = "SELECT * FROM $TABLE_CANDIDATURE;";


    const val TABLE_ENTREPRISE: String = "entreprises";
    const val COL_ID_ENTREPRISE: String = "id";
    const val COL_NAME_ENTREPRISE: String = "name";
    const val COL_EMAIL_ENTREPRISE: String = "email";
    const val COL_PASSWORD_ENTREPRISE: String = "password";
    const val COL_PHONE_ENTREPRISE: String = "phone";
    const val COL_VILLE_ENTREPRISE: String = "ville";

    const val CREATE_TABLE_ENTREPRISE: String = "CREATE TABLE $TABLE_ENTREPRISE (" +
            "$COL_ID_ENTREPRISE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_ENTREPRISE TEXT," +
            "$COL_EMAIL_ENTREPRISE TEXT," +
            "$COL_PASSWORD_ENTREPRISE TEXT," +
            "$COL_PHONE_ENTREPRISE TEXT," +
            "$COL_VILLE_ENTREPRISE TEXT" +
            ");";

    const val DROP_TABLE_ENTREPRISE: String = "DROP TABLE IF EXISTS $TABLE_ENTREPRISE;";
    const val SELECT_ALL_ENTREPRISE: String = "SELECT * FROM $TABLE_ENTREPRISE;";
}


