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
        db?.execSQL(Requetes.CREATE_TABLE_TEMPORARYWORKER)
        db?.execSQL(Requetes.CREATE_TABLE_EMPLOYER)
        db?.execSQL(Requetes.CREATE_TABLE_CANDIDATURE)
        db?.execSQL(Requetes.CREATE_TABLE_OFFRE_ENREGISTREE)

        create_default_user(db)
        create_default_offres(db)
    }

    private fun create_default_offres(db: SQLiteDatabase?) {
        val values = ContentValues()
        values.put(Requetes.COL_TITLE, "Java Developer")
        values.put(Requetes.COL_METIER, "IT")
        values.put(Requetes.COL_DESCRIPTION, "Looking for an experienced Java developer to join our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-06-01")
        values.put(Requetes.COL_DATE_FIN, "2023-12-31")
        values.put(Requetes.COL_REMUNERATION, 50000)
        values.put(Requetes.COL_CITY, "Montpellier")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 1)
        db?.insert(Requetes.TABLE_OFFRE, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Marketing Manager")
        values.put(Requetes.COL_METIER, "Marketing")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced marketing manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-08-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_CITY, "Montpellier")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 1)
        db?.insert(Requetes.TABLE_OFFRE, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Sales Manager")
        values.put(Requetes.COL_METIER, "Sales")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced sales manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-08-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_CITY, "Paris")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 1)
        db?.insert(Requetes.TABLE_OFFRE, null, values)
    }

    private fun create_default_user(db: SQLiteDatabase?){
        val values = ContentValues()
        values.put(Requetes.COL_NAME_TEMPORARYWORKER, "admin")
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, "admin")
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, "admin@admin.com")
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, "admin")
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, "0000000000")
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, "Montpellier")
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, "France")
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, "2000-01-01")
        values.put(Requetes.COL_COMMENTARY_TEMPORARYWORKER, "vide")
        db?.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "admin")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "admin")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "admin")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "admin")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer2@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, "toto")
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        db?.insert(Requetes.TABLE_EMPLOYER, null, values)
        values.clear()

    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("DB", "onUpgrade")
        db?.execSQL(Requetes.DROP_TABLE_OFFRE);
        db?.execSQL(Requetes.DROP_TABLE_TEMPORARYWORKERS);
        db?.execSQL(Requetes.DROP_TABLE_CANDIDATURE);
        db?.execSQL(Requetes.DROP_TABLE_EMPLOYERS);
        db?.execSQL(Requetes.DROP_TABLE_OFFRE_ENREGISTREE);

        onCreate(db);
    }
}