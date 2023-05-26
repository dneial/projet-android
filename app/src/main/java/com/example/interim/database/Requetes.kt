package com.example.interim.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Offre

object Requetes {


    const val TABLE_OFFRE: String = "offres";
    const val COL_ID: String = "id";
    const val COL_TITLE: String = "title";
    const val COL_METIER: String = "metier";
    const val COL_DESCRIPTION: String = "description";
    const val COL_DATE_DEBUT: String = "date_debut";
    const val COL_DATE_FIN: String = "date_fin";
    const val COL_REMUNERATION: String = "remuneration";
    const val COL_ID_OFFRE_ENTREPRISE: String = "id_entreprise";
    const val COL_CITY: String = "CITY";


    const val CREATE_TABLE_OFFRE: String = "CREATE TABLE $TABLE_OFFRE (" +
            "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_TITLE TEXT," +
            "$COL_METIER TEXT," +
            "$COL_DESCRIPTION TEXT," +
            "$COL_DATE_DEBUT DATE," +
            "$COL_DATE_FIN DATE," +
            "$COL_REMUNERATION TEXT," +
            "$COL_CITY TEXT," +
            "$COL_ID_OFFRE_ENTREPRISE INTEGER" +
            ");";

    const val DROP_TABLE_OFFRE: String = "DROP TABLE IF EXISTS $TABLE_OFFRE;";
    const val SELECT_ALL_OFFRE: String = "SELECT * FROM $TABLE_OFFRE;";


    const val TABLE_TEMPORARYWORKERS: String = "TemporaryWorkers2";
    const val COL_ID_TEMPORARYWORKER: String = "id";
    const val COL_NAME_TEMPORARYWORKER: String = "name";
    const val COL_LASTNAME_TEMPORARYWORKER: String = "lastname";
    const val COL_EMAIL_TEMPORARYWORKER: String = "email";
    const val COL_PASSWORD_TEMPORARYWORKER: String = "password";
    const val COL_PHONE_TEMPORARYWORKER: String = "phone";
    const val COL_CITY_TEMPORARYWORKER: String = "city";
    const val COL_BIRTHDAY_TEMPORARYWORKER: String = "birthday";
    const val COL_NATIONALITY_TEMPORARYWORKER: String = "nationality";
    const val COL_COMMENTARY_TEMPORARYWORKER: String = "comment";

    const val CREATE_TABLE_TEMPORARYWORKER: String = "CREATE TABLE $TABLE_TEMPORARYWORKERS (" +
            "$COL_ID_TEMPORARYWORKER INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_TEMPORARYWORKER TEXT," +
            "$COL_LASTNAME_TEMPORARYWORKER TEXT," +
            "$COL_EMAIL_TEMPORARYWORKER TEXT," +
            "$COL_PASSWORD_TEMPORARYWORKER TEXT," +
            "$COL_PHONE_TEMPORARYWORKER TEXT," +
            "$COL_CITY_TEMPORARYWORKER TEXT," +
            "$COL_BIRTHDAY_TEMPORARYWORKER TEXT," +
            "$COL_NATIONALITY_TEMPORARYWORKER TEXT," +
            "$COL_COMMENTARY_TEMPORARYWORKER TEXT" +
            ");";

    const val DROP_TABLE_TEMPORARYWORKERS: String = "DROP TABLE IF EXISTS $TABLE_TEMPORARYWORKERS;";
    const val SELECT_ALL_TEMPORARYWORKERS: String = "SELECT * FROM $TABLE_TEMPORARYWORKERS;";


    const val TABLE_EMLPLOYERS: String = "TemporaryWorkers";
    const val COL_ID_EMLPLOYER: String = "id";
    const val COL_NAME_EMLPLOYER: String = "name";
    const val COL_SERVICE_EMLPLOYER: String = "service";
    const val COL_SUBSERVICE_EMLPLOYER: String = "subService";
    const val COL_SIRET_EMLPLOYER: String = "SIRET";
    const val COL_CONTACT_EMLPLOYER: String = "contact";
    const val COL_SUBCONTACT_EMLPLOYER: String = "subContact";
    const val COL_EMAIL_EMLPLOYER: String = "email";
    const val COL_SUBEMAIL_EMLPLOYER: String = "secondEmail";
    const val COL_PHONE_EMLPLOYER: String = "phone";
    const val COL_SUBPHONE_EMLPLOYER: String = "subPhone";
    const val COL_ADDRESS_EMLPLOYER: String = "address";
    const val COL_PASSWORD_EMLPLOYER: String = "password";
    const val COL_COMMENTARY_EMLPLOYER: String = "commentary";

    const val CREATE_TABLE_EMPLOYER: String = "CREATE TABLE $TABLE_EMLPLOYERS (" +
            "$COL_ID_EMLPLOYER INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_EMLPLOYER TEXT," +
            "$COL_SERVICE_EMLPLOYER TEXT," +
            "$COL_SUBSERVICE_EMLPLOYER TEXT," +
            "$COL_SIRET_EMLPLOYER TEXT," +
            "$COL_CONTACT_EMLPLOYER TEXT," +
            "$COL_SUBCONTACT_EMLPLOYER TEXT," +
            "$COL_EMAIL_EMLPLOYER TEXT," +
            "$COL_SUBEMAIL_EMLPLOYER TEXT," +
            "$COL_PHONE_EMLPLOYER TEXT," +
            "$COL_SUBPHONE_EMLPLOYER TEXT" +
            "$COL_ADDRESS_EMLPLOYER TEXT" +
            "$COL_PASSWORD_EMLPLOYER TEXT" +
            "$COL_COMMENTARY_EMLPLOYER TEXT" +
            ");";

    const val DROP_TABLE_EMPLOYERS: String = "DROP TABLE IF EXISTS $TABLE_EMLPLOYERS;";
    const val SELECT_ALL_EMPLOYERS: String = "SELECT * FROM $TABLE_EMLPLOYERS;";



    const val TABLE_CANDIDATURE: String = "candidatures";
    const val COL_ID_CANDIDATURE: String = "id";
    const val COL_ID_OFFRE_CANDIDATURE: String = "id_offre";
    const val COL_ID_TEMPORARYWORKER_CANDIDATURE: String = "id_TEMPORARYWORKER";
    const val COL_STATUS_CANDIDATURE: String = "status";
    const val COL_DATE_CANDIDATURE: String = "date";

    const val CREATE_TABLE_CANDIDATURE: String = "CREATE TABLE $TABLE_CANDIDATURE (" +
            "$COL_ID_CANDIDATURE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_ID_OFFRE_CANDIDATURE INTEGER," +
            "$COL_ID_TEMPORARYWORKER_CANDIDATURE INTEGER," +
            "$COL_STATUS_CANDIDATURE TEXT," +
            "$COL_DATE_CANDIDATURE TEXT" +
            ");";

    const val DROP_TABLE_CANDIDATURE: String = "DROP TABLE IF EXISTS $TABLE_CANDIDATURE;";
    const val SELECT_ALL_CANDIDATURE: String = "SELECT * FROM $TABLE_CANDIDATURE;";
    const val SELECT_CANDIDATURE_BY_ID_TEMPORARYWORKER: String = "" +
            "SELECT * " +
            "FROM $TABLE_CANDIDATURE " +
            "INNER JOIN $TABLE_OFFRE " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRE.$COL_ID " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_TEMPORARYWORKER_CANDIDATURE = ?;";


    val SELECT_CANDIDATURE_DATE_AND_STATUS_AND_OFFRE_TITLE_BY_ID_EMPLOYEUR: String = "" +
            "SELECT $TABLE_CANDIDATURE.$COL_ID_CANDIDATURE, " +
            "$TABLE_TEMPORARYWORKERS.$COL_LASTNAME_TEMPORARYWORKER, " +
            "$TABLE_OFFRE.$COL_TITLE, " +
            "$TABLE_CANDIDATURE.$COL_DATE_CANDIDATURE, " +
            "$TABLE_CANDIDATURE.$COL_STATUS_CANDIDATURE " +
            "FROM $TABLE_CANDIDATURE " +
            "INNER JOIN $TABLE_OFFRE " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRE.$COL_ID " +
            "WHERE $TABLE_OFFRE.$COL_ID_OFFRE_ENTREPRISE = ?;";



    const val TABLE_OFFRE_ENREGISTREE: String = "offres_enregistrees";
    const val COL_ID_OFFRE_ENREGISTREE: String = "id_offre";
    const val COL_ID_USER_ENREGISTREE: String = "id_user";

    const val CREATE_TABLE_OFFRE_ENREGISTREE: String = "CREATE TABLE $TABLE_OFFRE_ENREGISTREE (" +
            "$COL_ID_OFFRE_ENREGISTREE INTEGER," +
            "$COL_ID_USER_ENREGISTREE INTEGER," +
            "PRIMARY KEY ($COL_ID_OFFRE_ENREGISTREE, $COL_ID_USER_ENREGISTREE));";

    const val DROP_TABLE_OFFRE_ENREGISTREE: String = "DROP TABLE IF EXISTS $TABLE_OFFRE_ENREGISTREE;";

    const val OFFRE_ENREGISTREE_GET_BY_USER_ID: String = "" +
            "SELECT * " +
            "FROM $TABLE_OFFRE_ENREGISTREE " +
            "INNER JOIN $TABLE_OFFRE " +
            "ON $TABLE_OFFRE_ENREGISTREE.$COL_ID_OFFRE_ENREGISTREE = $TABLE_OFFRE.$COL_ID " +
            "WHERE $COL_ID_USER_ENREGISTREE = ?;";


    const val GET_OFFRE_BY_CANDIDATURE_ID: String = "" +
            "SELECT * " +
            "FROM $TABLE_OFFRE " +
            "INNER JOIN $TABLE_CANDIDATURE " +
            "ON $TABLE_OFFRE.$COL_ID = $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_CANDIDATURE = ?;";


}




