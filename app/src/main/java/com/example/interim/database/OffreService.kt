package com.example.interim.database

import android.content.ContentValues
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
        return id > 0
    }

    fun readAll(): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID_OFFRE} DESC"
        val cursor = db.query(Requetes.TABLE_OFFRE, null, null, null, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {
            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)

            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
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
                "WHERE ${Requetes.COL_ID_OFFRE} = ${offre.id};";
    }

    fun filter(ville: String): ArrayList<Offre> {
        val sortOrder = "${Requetes.COL_ID_OFFRE} DESC"
        val selection = "${Requetes.COL_CITY} = ?"
        val selectionArgs = arrayOf(ville)
        val cursor = db.query(Requetes.TABLE_OFFRE, null, selection, selectionArgs, null, null, sortOrder);
        val offres = ArrayList<Offre>();

        while (cursor.moveToNext()) {

            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)

            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
            );
            offres.add(offre);
        }
        cursor.close();

        Log.d("offres", offres.toString())
        return offres;
    }


    fun getEnregistresByUser(user_id: Long): ArrayList<Offre> {
        val selectionArgs = arrayOf(user_id.toString())
        val offres = ArrayList<Offre>();
        val cursor = db.rawQuery(Requetes.OFFRE_ENREGISTREE_GET_BY_USER_ID, selectionArgs)
        while (cursor.moveToNext()) {

            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)

            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
            );
            offres.add(offre);
        }

        cursor.close();
        Log.d("offres", offres.toString())
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

            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
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

            val employer_id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE_EMPLOYER))
            val employer = UsersService().getEmployer(employer_id)
            val offre = Offre(
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_METIER)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DESCRIPTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_DEBUT)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_FIN)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_REMUNERATION)),
                cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_OFFRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY)),
                employer!!
            );
            offres.add(offre);
        }

        cursor.close();
        return offres;
    }
}