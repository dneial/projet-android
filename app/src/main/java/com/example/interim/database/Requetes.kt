package com.example.interim.database

object Requetes {


    const val TABLE_OFFRES: String = "offres";
    const val COL_ID_OFFRE: String = "id_offre";
    const val COL_TITLE: String = "title";
    const val COL_METIER: String = "metier";
    const val COL_DESCRIPTION: String = "description";
    const val COL_DATE_DEBUT: String = "date_debut";
    const val COL_DATE_FIN: String = "date_fin";
    const val COL_REMUNERATION: String = "remuneration";
    const val COL_ID_OFFRE_EMPLOYER: String = "id_employer";
    const val COL_CITY: String = "CITY";
    const val COL_DATE_CREATION_OFFRE: String = "date_creation";


    const val CREATE_TABLE_OFFRE: String = "CREATE TABLE $TABLE_OFFRES (" +
            "$COL_ID_OFFRE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_TITLE TEXT," +
            "$COL_METIER TEXT," +
            "$COL_DESCRIPTION TEXT," +
            "$COL_DATE_DEBUT DATE," +
            "$COL_DATE_FIN DATE," +
            "$COL_REMUNERATION TEXT," +
            "$COL_CITY TEXT," +
            "$COL_ID_OFFRE_EMPLOYER INTEGER," +
            "$COL_DATE_CREATION_OFFRE TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    const val DROP_TABLE_OFFRE: String = "DROP TABLE IF EXISTS $TABLE_OFFRES;";
    const val SELECT_ALL_OFFRE: String = "SELECT * FROM $TABLE_OFFRES;";


    const val TABLE_TEMPORARYWORKERS: String = "TemporaryWorkers";
    const val COL_ID_TEMPORARYWORKER: String = "id_temporaryworker";
    const val COL_NAME_TEMPORARYWORKER: String = "name";
    const val COL_LASTNAME_TEMPORARYWORKER: String = "lastname";
    const val COL_EMAIL_TEMPORARYWORKER: String = "email";
    const val COL_PASSWORD_TEMPORARYWORKER: String = "password";
    const val COL_PHONE_TEMPORARYWORKER: String = "phone";
    const val COL_CITY_TEMPORARYWORKER: String = "city";
    const val COL_BIRTHDAY_TEMPORARYWORKER: String = "birthday";
    const val COL_NATIONALITY_TEMPORARYWORKER: String = "nationality";
    const val COL_COMMENTARY_TEMPORARYWORKER: String = "comment";
    const val COL_DATE_CREATION_TEMPORARYWORKER: String = "date_creation";

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
            "$COL_COMMENTARY_TEMPORARYWORKER TEXT," +
            "$COL_DATE_CREATION_TEMPORARYWORKER TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    const val DROP_TABLE_TEMPORARYWORKERS: String = "DROP TABLE IF EXISTS $TABLE_TEMPORARYWORKERS;";
    const val SELECT_ALL_TEMPORARYWORKERS: String = "SELECT * FROM $TABLE_TEMPORARYWORKERS;";


    const val TABLE_EMPLOYERS: String = "Employers";
    const val COL_ID_EMPLOYER: String = "id_employer";
    const val COL_NAME_EMPLOYER: String = "name";
    const val COL_SERVICE_EMPLOYER: String = "service";
    const val COL_SUBSERVICE_EMPLOYER: String = "subService";
    const val COL_SIRET_EMPLOYER: String = "SIRET";
    const val COL_CONTACT_EMPLOYER: String = "contact";
    const val COL_SUBCONTACT_EMPLOYER: String = "subContact";
    const val COL_EMAIL_EMPLOYER: String = "email";
    const val COL_SUBEMAIL_EMPLOYER: String = "secondEmail";
    const val COL_PHONE_EMPLOYER: String = "phone";
    const val COL_SUBPHONE_EMPLOYER: String = "subPhone";
    const val COL_ADDRESS_EMPLOYER: String = "address";
    const val COL_PASSWORD_EMPLOYER: String = "password";
    const val COL_COMMENTARY_EMPLOYER: String = "commentary";
    const val COL_DATE_CREATION_EMPLOYER: String = "date_creation";
    const val COL_STATUS_EMPLOYER: String = "status";

    const val CREATE_TABLE_EMPLOYER: String = "CREATE TABLE $TABLE_EMPLOYERS (" +
            "$COL_ID_EMPLOYER INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_NAME_EMPLOYER TEXT," +
            "$COL_SERVICE_EMPLOYER TEXT," +
            "$COL_SUBSERVICE_EMPLOYER TEXT," +
            "$COL_SIRET_EMPLOYER TEXT," +
            "$COL_CONTACT_EMPLOYER TEXT," +
            "$COL_SUBCONTACT_EMPLOYER TEXT," +
            "$COL_EMAIL_EMPLOYER TEXT," +
            "$COL_SUBEMAIL_EMPLOYER TEXT," +
            "$COL_PHONE_EMPLOYER TEXT," +
            "$COL_SUBPHONE_EMPLOYER TEXT, " +
            "$COL_ADDRESS_EMPLOYER TEXT, " +
            "$COL_PASSWORD_EMPLOYER TEXT, " +
            "$COL_COMMENTARY_EMPLOYER TEXT," +
            "$COL_STATUS_EMPLOYER INTERGER(1), " +
            "$COL_DATE_CREATION_EMPLOYER TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    const val DROP_TABLE_EMPLOYERS: String = "DROP TABLE IF EXISTS $TABLE_EMPLOYERS;";
    const val SELECT_ALL_EMPLOYERS: String = "SELECT * FROM $TABLE_EMPLOYERS;";

    const val TABLE_ADMINS: String = "Admins";
    const val COL_ID_ADMIN: String = "id_admin";
    const val COL_EMAIL_ADMIN: String = "email";
    const val COL_PASSWORD_ADMIN: String = "password";
    const val COL_DATE_CREATION_ADMIN: String = "date_creation";

    const val CREATE_TABLE_ADMIN: String = "CREATE TABLE $TABLE_ADMINS (" +
            "$COL_ID_ADMIN INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_EMAIL_ADMIN TEXT," +
            "$COL_PASSWORD_ADMIN TEXT," +
            "$COL_DATE_CREATION_ADMIN TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");";

    const val DROP_TABLE_ADMINS: String = "DROP TABLE IF EXISTS $TABLE_ADMINS;";
    const val SELECT_ALL_ADMINS: String = "SELECT * FROM $TABLE_ADMINS;";

    const val TABLE_REPORTS: String = "Reports";
    const val COL_ID_REPORT: String = "id_report";
    const val COL_EMAIL_REPORT: String = "email";
    const val COL_COMMENTARY_REPORT: String = "commentary";

    const val CREATE_TABLE_REPORT: String = "CREATE TABLE $TABLE_REPORTS (" +
            "$COL_ID_REPORT INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_EMAIL_REPORT TEXT," +
            "$COL_ID_OFFRE TEXT," +
            "$COL_COMMENTARY_REPORT TEXT" +
            ");";

    const val DROP_TABLE_REPORTS: String = "DROP TABLE IF EXISTS $TABLE_REPORTS;";
    const val SELECT_ALL_REPORTS: String = "" +
            "SELECT * " +
            "FROM $TABLE_REPORTS " +
            "INNER JOIN $TABLE_OFFRES " +
            "ON $TABLE_REPORTS.$COL_ID_OFFRE = $TABLE_OFFRES.$COL_ID_OFFRE;"







    const val TABLE_CANDIDATURE: String = "candidatures";
    const val COL_ID_CANDIDATURE: String = "id_candidature";
    const val COL_ID_OFFRE_CANDIDATURE: String = "id_offre";
    const val COL_ID_TEMPORARYWORKER_CANDIDATURE: String = "id_TEMPORARYWORKER";
    const val COL_STATUS_CANDIDATURE: String = "status";
    const val COL_DATE_CANDIDATURE: String = "date";

    const val CREATE_TABLE_CANDIDATURE: String = "CREATE TABLE $TABLE_CANDIDATURE (" +
            "$COL_ID_CANDIDATURE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_ID_OFFRE_CANDIDATURE INTEGER," +
            "$COL_ID_TEMPORARYWORKER_CANDIDATURE INTEGER," +
            "$COL_STATUS_CANDIDATURE TEXT," +
            "$COL_DATE_CANDIDATURE DATE" +
            ");";

    const val DROP_TABLE_CANDIDATURE: String = "DROP TABLE IF EXISTS $TABLE_CANDIDATURE;";
    const val SELECT_ALL_CANDIDATURE: String = "SELECT * FROM $TABLE_CANDIDATURE;";
    const val SELECT_CANDIDATURE_BY_ID_TEMPORARYWORKER: String = "" +
            "SELECT * " +
            "FROM $TABLE_CANDIDATURE " +
            "INNER JOIN $TABLE_OFFRES " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRES.$COL_ID_OFFRE " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_TEMPORARYWORKER_CANDIDATURE = ?;";


    const val SELECT_CANDIDATURE_BY_ID_EMPLOYER: String = "" +
            "SELECT * " +
            "FROM $TABLE_CANDIDATURE " +
            "INNER JOIN $TABLE_OFFRES " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRES.$COL_ID_OFFRE " +
            "WHERE $TABLE_OFFRES.$COL_ID_OFFRE_EMPLOYER = ?;";



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
            "INNER JOIN $TABLE_OFFRES " +
            "ON $TABLE_OFFRE_ENREGISTREE.$COL_ID_OFFRE_ENREGISTREE = $TABLE_OFFRES.$COL_ID_OFFRE " +
            "WHERE $COL_ID_USER_ENREGISTREE = ?;";




    const val GET_CANDIDATURE_BY_ID: String = "" +
            "SELECT * " +
            "FROM $TABLE_OFFRES " +
            "INNER JOIN $TABLE_CANDIDATURE " +
            "ON $TABLE_OFFRES.$COL_ID_OFFRE = $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_CANDIDATURE = ?;";

    const val CHECK_UNIQUE_EMAIL: String = "" +
            "SELECT * " +
            "FROM " +
            "(SELECT $COL_EMAIL_EMPLOYER FROM $TABLE_EMPLOYERS " +
            "UNION ALL " +
            "SELECT $COL_EMAIL_TEMPORARYWORKER FROM $TABLE_TEMPORARYWORKERS) AS combined " +
            "WHERE email = ?;";

    const val CHECK_STATUS_EMPLOYER: String = "" +
            "SELECT * " +
            "FROM $TABLE_EMPLOYERS " +
            "WHERE $COL_ID_EMPLOYER = ? AND $COL_STATUS_EMPLOYER IS TRUE;";

}
