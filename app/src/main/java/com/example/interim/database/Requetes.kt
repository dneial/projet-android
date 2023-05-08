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
}


class OffreService(var context: Context) {

    var db: SQLiteDatabase = MaBaseOpenHelper(context).readableDatabase


        fun create(offre: Offre): Boolean {
            val query = "INSERT INTO ${Requetes.TABLE_OFFRE} (" +
                    "${Requetes.COL_NAME}, " +
                    "${Requetes.COL_METIER}, " +
                    "${Requetes.COL_DESCRIPTION}, " +
                    "${Requetes.COL_DATE_DEBUT}, " +
                    "${Requetes.COL_DATE_FIN}, " +
                    "${Requetes.COL_REMUNERATION}, " +
                    "${Requetes.COL_ID_ENTREPRISE}" +
                    ") VALUES (" +
                    "'${offre.title}', " +
                    "'${offre.metier}', " +
                    "'${offre.description}', " +
                    "'${offre.date_debut}', " +
                    "'${offre.date_fin}', " +
                    "'${offre.remuneration}', " +
                    "'${offre.id_entreprise}'" +
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
            val query = Requetes.SELECT_ALL_OFFRE;
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
                    cursor.getInt(cursor.getColumnIndexOrThrow(Requetes.COL_ID))
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
                    "${Requetes.COL_ID_ENTREPRISE} = '${offre.id_entreprise}' " +
                    "WHERE ${Requetes.COL_ID} = ${offre.id};";
        }
}