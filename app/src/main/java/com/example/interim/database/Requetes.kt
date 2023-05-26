package com.example.interim.database

object Requetes {


    const val TABLE_OFFRE: String = "offres";
    const val COL_ID_OFFRE: String = "id_offre";
    const val COL_TITLE: String = "title";
    const val COL_METIER: String = "metier";
    const val COL_DESCRIPTION: String = "description";
    const val COL_DATE_DEBUT: String = "date_debut";
    const val COL_DATE_FIN: String = "date_fin";
    const val COL_REMUNERATION: String = "remuneration";
    const val COL_ID_OFFRE_EMPLOYER: String = "id_employer";
    const val COL_CITY: String = "CITY";


    const val CREATE_TABLE_OFFRE: String = "CREATE TABLE $TABLE_OFFRE (" +
            "$COL_ID_OFFRE INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$COL_TITLE TEXT," +
            "$COL_METIER TEXT," +
            "$COL_DESCRIPTION TEXT," +
            "$COL_DATE_DEBUT DATE," +
            "$COL_DATE_FIN DATE," +
            "$COL_REMUNERATION TEXT," +
            "$COL_CITY TEXT," +
            "$COL_ID_OFFRE_EMPLOYER INTEGER" +
            ");";

    const val DROP_TABLE_OFFRE: String = "DROP TABLE IF EXISTS $TABLE_OFFRE;";
    const val SELECT_ALL_OFFRE: String = "SELECT * FROM $TABLE_OFFRE;";


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


    const val TABLE_EMPLOYER: String = "Employers";
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

    const val CREATE_TABLE_EMPLOYER: String = "CREATE TABLE $TABLE_EMPLOYER (" +
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
            "$COL_COMMENTARY_EMPLOYER TEXT" +
            ");";

    const val DROP_TABLE_EMPLOYERS: String = "DROP TABLE IF EXISTS $TABLE_EMPLOYER;";
    const val SELECT_ALL_EMPLOYERS: String = "SELECT * FROM $TABLE_EMPLOYER;";



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
            "INNER JOIN $TABLE_OFFRE " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRE.$COL_ID_OFFRE " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_TEMPORARYWORKER_CANDIDATURE = ?;";


    const val SELECT_CANDIDATURE_BY_ID_EMPLOYER: String = "" +
            "SELECT * " +
            "FROM $TABLE_CANDIDATURE " +
            "INNER JOIN $TABLE_OFFRE " +
            "ON $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE = $TABLE_OFFRE.$COL_ID_OFFRE " +
            "WHERE $TABLE_OFFRE.$COL_ID_OFFRE_EMPLOYER = ?;";



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
            "ON $TABLE_OFFRE_ENREGISTREE.$COL_ID_OFFRE_ENREGISTREE = $TABLE_OFFRE.$COL_ID_OFFRE " +
            "WHERE $COL_ID_USER_ENREGISTREE = ?;";


    const val GET_CANDIDATURE_BY_ID: String = "" +
            "SELECT * " +
            "FROM $TABLE_OFFRE " +
            "INNER JOIN $TABLE_CANDIDATURE " +
            "ON $TABLE_OFFRE.$COL_ID_OFFRE = $TABLE_CANDIDATURE.$COL_ID_OFFRE_CANDIDATURE " +
            "WHERE $TABLE_CANDIDATURE.$COL_ID_CANDIDATURE = ?;";
}




