package com.example.interim.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Offre

class OffreService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(offre: Offre): Boolean {
        val query = "INSERT INTO ${Requetes.TABLE_OFFRE} (" +
                "${Requetes.COL_TITLE}, " +
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
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
            );
            offres.add(offre);
        }
        cursor.close();
        return offres;
    }

    fun update(offre: Offre) {
        val query = "UPDATE ${Requetes.TABLE_OFFRE} SET " +
                "${Requetes.COL_TITLE} = '${offre.title}', " +
                "${Requetes.COL_METIER} = '${offre.metier}', " +
                "${Requetes.COL_DESCRIPTION} = '${offre.description}', " +
                "${Requetes.COL_DATE_DEBUT} = '${offre.date_debut}', " +
                "${Requetes.COL_DATE_FIN} = '${offre.date_fin}', " +
                "${Requetes.COL_REMUNERATION} = '${offre.remuneration}', " +
                "WHERE ${Requetes.COL_ID} = ${offre.id};";
    }

    fun filter(ville: String): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID} DESC"
        val selection = "${Requetes.COL_CITY} = ?"
        val selectionArgs = arrayOf(ville)
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
            );
            offres.add(offre);
        }
        cursor.close();

        Log.d("offres", offres.toString())
        return offres;
    }


    fun getEnregistresByUser(user_id: Long): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID_OFFRE_ENREGISTREE} DESC"
        val selection = "${Requetes.COL_ID_USER_ENREGISTREE} = ?"
        val selectionArgs = arrayOf(user_id.toString())
        val cursor = db.query(Requetes.TABLE_OFFRE_ENREGISTREE, arrayOf(Requetes.COL_ID_OFFRE_ENREGISTREE), selection, selectionArgs, null, null, sortOrder)
        val offres = ArrayList<Long>();

        while (cursor.moveToNext()) {
            val offre = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_ENREGISTREE));
            offres.add(offre);
        }

        cursor.close();
        Log.d("offres ids", offres.toString())
        return readAllIn(offres);
    }

    private fun readAllIn(ids: ArrayList<Long>): ArrayList<Offre> {
        val selection = "${Requetes.COL_ID} in (?)"
        val selectionArgs = arrayOf(ids.toString())

        val cursor = db.query(
            Requetes.TABLE_OFFRE,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        );


        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
            );
            offres.add(offre);
        }
        cursor.close();
        Log.d("offres enregistrees", offres.toString())
        return offres;
    }

    fun enregistrer(user_id: Long, offre_id: Long): Long {
        val values = ContentValues().apply {
            put(Requetes.COL_ID_USER_ENREGISTREE, user_id)
            put(Requetes.COL_ID_OFFRE_ENREGISTREE, offre_id)
        }

        return db.insert(Requetes.TABLE_OFFRE_ENREGISTREE, null, values)
    }

    fun getOffre(offreId: Long): Offre? {
        val selection = "${Requetes.COL_ID} = ?"
        val selectionArgs = arrayOf(offreId.toString())
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, null);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))
            );
            offres.add(offre);
        }
        cursor.close();

        if (offres.size > 0) {
            return offres[0]
        } else {
            return null
        }

    }
}