package com.example.interim.database

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Offre

class OffreService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(offre: Offre): Boolean {
        val query = "INSERT INTO ${Requetes.TABLE_OFFRE} (" +
                "${Requetes.COL_NAME}, " +
                "${Requetes.COL_METIER}, " +
                "${Requetes.COL_DESCRIPTION}, " +
                "${Requetes.COL_DATE_DEBUT}, " +
                "${Requetes.COL_DATE_FIN}, " +
                "${Requetes.COL_REMUNERATION}, " +
                ") VALUES (" +
                "'${offre.title}', " +
                "'${offre.metier}', " +
                "'${offre.description}', " +
                "'${offre.date_debut}', " +
                "'${offre.date_fin}', " +
                "'${offre.remuneration}', " +
                ");";

        return try{
            db.execSQL(query)
            true
        } catch (e: Exception) {
            Log.d("error", e.toString())
            false
        }

    }

    fun readAll(): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID} DESC"
        val cursor = db.query(Requetes.TABLE_OFFRE, null, null, null, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_VILLE))
            );
            offres.add(offre);
        }
        cursor.close();

        Log.d("OffreService", offres.toString())
        return offres;
    }

    fun update(offre: Offre) {
        val query = "UPDATE ${Requetes.TABLE_OFFRE} SET " +
                "${Requetes.COL_NAME} = '${offre.title}', " +
                "${Requetes.COL_METIER} = '${offre.metier}', " +
                "${Requetes.COL_DESCRIPTION} = '${offre.description}', " +
                "${Requetes.COL_DATE_DEBUT} = '${offre.date_debut}', " +
                "${Requetes.COL_DATE_FIN} = '${offre.date_fin}', " +
                "${Requetes.COL_REMUNERATION} = '${offre.remuneration}', " +
                "WHERE ${Requetes.COL_ID} = ${offre.id};";
    }
}