package com.example.interim.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.security.MessageDigest

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
        db?.execSQL(Requetes.CREATE_TABLE_REPORT)
        db?.execSQL(Requetes.CREATE_TABLE_ADMIN)

        create_default_user(db)
        create_default_offres(db)
        create_report(db)
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
        values.put(Requetes.COL_DATE_CREATION_OFFRE, "2022-06-01:12:00:00")
        db?.insert(Requetes.TABLE_OFFRES, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Marketing Manager")
        values.put(Requetes.COL_METIER, "Marketing")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced marketing manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-08-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_CITY, "Montpellier")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 1)
        values.put(Requetes.COL_DATE_CREATION_OFFRE, "2023-05-01:12:00:00")
        db?.insert(Requetes.TABLE_OFFRES, null, values)

        values.clear()
        values.put(Requetes.COL_TITLE, "Sales Manager")
        values.put(Requetes.COL_METIER, "Sales")
        values.put(Requetes.COL_DESCRIPTION,"We are seeking an experienced sales manager to lead our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-07-01")
        values.put(Requetes.COL_DATE_FIN, "2024-08-30")
        values.put(Requetes.COL_REMUNERATION, 70000)
        values.put(Requetes.COL_CITY, "Paris")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 1)
        values.put(Requetes.COL_DATE_CREATION_OFFRE, "2023-06-03:12:00:00")
        db?.insert(Requetes.TABLE_OFFRES, null, values)


        values.clear()
        values.put(Requetes.COL_TITLE, "Kotlin Developer")
        values.put(Requetes.COL_METIER, "IT")
        values.put(Requetes.COL_DESCRIPTION, "Looking for an experienced Kotlin developer to join our team")
        values.put(Requetes.COL_DATE_DEBUT, "2023-06-01")
        values.put(Requetes.COL_DATE_FIN, "2023-12-31")
        values.put(Requetes.COL_REMUNERATION, 50000)
        values.put(Requetes.COL_CITY, "Nice")
        values.put(Requetes.COL_ID_OFFRE_EMPLOYER, 2)
        values.put(Requetes.COL_DATE_CREATION_OFFRE, "2023-06-04:12:00:00")
        db?.insert(Requetes.TABLE_OFFRES, null, values)


    }

    private fun create_default_user(db: SQLiteDatabase?){
        val values = ContentValues()
        val mdp1 = MessageDigest.getInstance("SHA-256").digest("interim".toByteArray()).fold("", { str, it -> str + "%02x".format(it) }).toString()
        val mdp2 = MessageDigest.getInstance("SHA-256").digest("toto".toByteArray()).fold("", { str, it -> str + "%02x".format(it) }).toString()
        val mdp3 = MessageDigest.getInstance("SHA-256").digest("admin".toByteArray()).fold("", { str, it -> str + "%02x".format(it) }).toString()

        values.put(Requetes.COL_NAME_TEMPORARYWORKER, "interim")
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, "interim")
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, "interim@interim.com")
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, mdp1)
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, "0000000000")
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, "2000-01-01")
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, "Montpellier")
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, "France")
        values.put(Requetes.COL_COMMENTARY_TEMPORARYWORKER, "vide")
        values.put(Requetes.COL_DATE_CREATION_TEMPORARYWORKER, "2022-09-01:12:00:00")
        db?.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer2@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, mdp2)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        values.put(Requetes.COL_STATUS_EMPLOYER, 1)
        values.put(Requetes.COL_DATE_CREATION_EMPLOYER, "2022-09-01:12:00:00")
        db?.insert(Requetes.TABLE_EMPLOYERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer2")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer2@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer3@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, mdp2)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        values.put(Requetes.COL_STATUS_EMPLOYER, 1)
        values.put(Requetes.COL_DATE_CREATION_EMPLOYER, "2023-06-01:12:00:00")
        db?.insert(Requetes.TABLE_EMPLOYERS, null, values)
        values.clear()

        values.put(Requetes.COL_EMAIL_ADMIN, "admin")
        values.put(Requetes.COL_PASSWORD_ADMIN, mdp3)
        db?.insert(Requetes.TABLE_ADMINS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_TEMPORARYWORKER, "interim2")
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, "interim")
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, "interim2@interim.com")
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, mdp1)
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, "1999-05-25:12:00:00")
        values.put(Requetes.COL_DATE_CREATION_TEMPORARYWORKER, "2023-06-05:12:00:00")
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, "0000000000")
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, "Montpellier")
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, "France")
        db?.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_TEMPORARYWORKER, "interim3")
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, "interim")
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, "interim3@interim.com")
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, mdp1)
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, "1999-05-25:12:00:00")
        values.put(Requetes.COL_DATE_CREATION_TEMPORARYWORKER, "2023-05-25:12:00:00")
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, "0000000000")
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, "Montpellier")
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, "France")
        db?.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_TEMPORARYWORKER, "interim4")
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, "interim")
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, "interim4@interim.com")
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, mdp1)
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, "1999-05-25:12:00:00")
        values.put(Requetes.COL_DATE_CREATION_TEMPORARYWORKER, "2023-04-25:12:00:00")
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, "0000000000")
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, "Montpellier")
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, "France")
        db?.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer3")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer3@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer3@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, mdp2)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        values.put(Requetes.COL_STATUS_EMPLOYER, 0)
        values.put(Requetes.COL_DATE_CREATION_EMPLOYER, "2023-06-05:12:00:00")
        db?.insert(Requetes.TABLE_EMPLOYERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer4")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer4@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer3@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, mdp2)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        values.put(Requetes.COL_STATUS_EMPLOYER, 0)
        values.put(Requetes.COL_DATE_CREATION_EMPLOYER, "2023-06-25:12:00:00")
        db?.insert(Requetes.TABLE_EMPLOYERS, null, values)
        values.clear()

        values.put(Requetes.COL_NAME_EMPLOYER, "employer5")
        values.put(Requetes.COL_SERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, "employer")
        values.put(Requetes.COL_SIRET_EMPLOYER, "12312312312345")
        values.put(Requetes.COL_CONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, "employer")
        values.put(Requetes.COL_EMAIL_EMPLOYER, "employer2@employer.com")
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, "employer5@employer.com")
        values.put(Requetes.COL_PHONE_EMPLOYER, "0123456789")
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, "9876543210")
        values.put(Requetes.COL_ADDRESS_EMPLOYER, "52 rue montmartre 75002 Paris")
        values.put(Requetes.COL_PASSWORD_EMPLOYER, mdp2)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER, "commentaire")
        values.put(Requetes.COL_STATUS_EMPLOYER, 0)
        values.put(Requetes.COL_DATE_CREATION_EMPLOYER, "2023-07-25:12:00:00")
        db?.insert(Requetes.TABLE_EMPLOYERS, null, values)
        values.clear()

    }

    private fun create_report(db: SQLiteDatabase?){
        val values = ContentValues()
        values.put(Requetes.COL_ID_REPORT, 1)
        values.put(Requetes.COL_EMAIL_REPORT, "interim@interim.com")
        values.put(Requetes.COL_COMMENTARY_REPORT, "hgjfghjfghjfghjmdfglihjsdfmglkjsfdmoigjsdrfmoifgjsqdmflkgjsdmlkfgjsmdlkfjgsmd lfkgjnsdmofugn,smdiofun,gpsidfunglmiusdf")
        values.put(Requetes.COL_ID_OFFRE, 1)
        db?.insert(Requetes.TABLE_REPORTS, null, values)


        values.clear()
        values.put(Requetes.COL_ID_REPORT, 2)
        values.put(Requetes.COL_EMAIL_REPORT, "interim@interim.com")
        values.put(Requetes.COL_COMMENTARY_REPORT, "commentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentaire" +
                "commentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentairecommentaire")
        values.put(Requetes.COL_ID_OFFRE, 2)
        db?.insert(Requetes.TABLE_REPORTS, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(Requetes.DROP_TABLE_OFFRE);
        db?.execSQL(Requetes.DROP_TABLE_TEMPORARYWORKERS);
        db?.execSQL(Requetes.DROP_TABLE_CANDIDATURE);
        db?.execSQL(Requetes.DROP_TABLE_EMPLOYERS);
        db?.execSQL(Requetes.DROP_TABLE_OFFRE_ENREGISTREE);

        onCreate(db);
    }
}