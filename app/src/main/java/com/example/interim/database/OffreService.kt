package com.example.interim.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.models.Offre
import com.example.interim.services.UsersService

class OffreService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(offre: Offre): Boolean {
        val values = offre.toContentValues()
        val id = db.insert(Requetes.TABLE_OFFRE, null, values)
        offre.id = id
        return id > 0
    }

    fun readAll(): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID_OFFRE} DESC"
        val cursor = db.query(Requetes.TABLE_OFFRE, null, null, null, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }
        cursor.close();
        return offres;
    }

    fun delete(offre_id: Long): Boolean {
        val selection = "${Requetes.COL_ID_OFFRE} = ?"
        val selectionArgs = arrayOf(offre_id.toString())
        return db.delete(Requetes.TABLE_OFFRE, selection, selectionArgs) > 0
    }

    fun update(offre: Offre) {
        val values = offre.toContentValues()
        val selection = "${Requetes.COL_ID_OFFRE} = ?"
        val selectionArgs = arrayOf(offre.id.toString())
        db.update(Requetes.TABLE_OFFRE, values, selection, selectionArgs)
        Log.d("update", "offre updated: $offre")


    }

    fun filter(ville: String): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID_OFFRE} DESC"
        val selection = "${Requetes.COL_CITY} = ?"
        val selectionArgs = arrayOf(ville)
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }
        cursor.close();

        return offres;
    }


    fun getEnregistresByUser(user_id: Long): ArrayList<Offre> {
        val selectionArgs = arrayOf(user_id.toString())
        val offres = ArrayList<Offre>();
        val cursor = db.rawQuery(Requetes.OFFRE_ENREGISTREE_GET_BY_USER_ID, selectionArgs)
        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }

        cursor.close();
        return offres;
    }


    fun enregistrer(user_id: Long, offre_id: Long): Long {
        val values = ContentValues().apply {
            put(Requetes.COL_ID_USER_ENREGISTREE, user_id)
            put(Requetes.COL_ID_OFFRE_ENREGISTREE, offre_id)
        }
        var id = -1L
        try {
            id = db.insertOrThrow(Requetes.TABLE_OFFRE_ENREGISTREE, null, values)
        } catch (e: SQLiteConstraintException) {
            Log.d("error", e.toString())
        }
        return id
    }

    fun getOffre(offreId: Long): Offre? {
        val selection = "${Requetes.COL_ID_OFFRE} = ?"
        val selectionArgs = arrayOf(offreId.toString())
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, null);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }
        cursor.close();

        if (offres.size > 0) {
            return offres[0]
        } else {
            return null
        }

    }

    fun filter(values: ContentValues): ArrayList<Offre> {
        val queryBuilder = StringBuilder()
        queryBuilder.append("SELECT * FROM ${Requetes.TABLE_OFFRE}")
        if (values.size() > 0) {
            queryBuilder.append(" WHERE")
            for (key in values.keySet()) {
                when(key) {
                    "title" -> queryBuilder.append(" ${Requetes.COL_TITLE} LIKE '%${values.getAsString(key)}%' AND")
                    "metier" -> queryBuilder.append(" ${Requetes.COL_METIER} LIKE '%${values.getAsString(key)}%' AND")
                    "description" -> queryBuilder.append(" ${Requetes.COL_DESCRIPTION} LIKE '%${values.getAsString(key)}%' AND")
                    "date_debut" -> queryBuilder.append(" ${Requetes.COL_DATE_DEBUT} < '${values.getAsString(key)}' AND")
                    "date_fin" -> queryBuilder.append(" ${Requetes.COL_DATE_FIN} > '${values.getAsString(key)}' AND")
                    "remuneration" -> queryBuilder.append(" ${Requetes.COL_REMUNERATION} LIKE '%${values.getAsString(key)}%' AND")
                    "ville" -> queryBuilder.append(" ${Requetes.COL_CITY} LIKE '%${values.getAsString(key)}%' AND")
                    else -> {}
                }
            }

            // Remove the trailing "AND" from the query
            queryBuilder.setLength(queryBuilder.length - 4)
        }
        Log.d("query", queryBuilder.toString())
        val cursor = db.rawQuery(queryBuilder.toString(), null)
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }

        cursor.close();
        return offres;
    }

    fun getPublieesByEmployer(userId: Long): List<Offre>? {
        val selection = "${Requetes.COL_ID_OFFRE_EMPLOYER} = ?"
        val selectionArgs = arrayOf(userId.toString())
        val offres = ArrayList<Offre>();
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            val offre = cursorToOffre(cursor)
            offres.add(offre);
        }
        cursor.close();
        return offres;
    }

    companion object {
        fun cursorToOffre(cursor: Cursor): Offre {

            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)
            val title = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE))
            val metier = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION))
            val date_debut = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT))
            val date_fin = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN))
            val remuneration = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION))
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE))
            val city = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY))

            Log.d("cursortoffre", Offre(
                title=title,
                metier=metier,
                description=description,
                date_debut=date_debut,
                date_fin=date_fin,
                remuneration=remuneration,
                id=id,
                ville=city,
                employer=employer!!
            ).toString())

            return Offre(
                title=title,
                metier=metier,
                description=description,
                date_debut=date_debut,
                date_fin=date_fin,
                remuneration=remuneration,
                id=id,
                ville=city,
                employer=employer!!
            )
        }
    }

}