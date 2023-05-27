package com.example.interim.services

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.User

class UsersService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(temporaryWorker: TemporaryWorker){
        Log.d("create", "create: $temporaryWorker")
        val values = temporaryWorker.toContentValues()
        val id = db.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        temporaryWorker.setId(id)
    }

    fun create(employer: Employer){
        Log.d("create", "create: $employer")
        val values = employer.toContentValues()
        val id = db.insert(Requetes.TABLE_EMPLOYER, null, values)
        employer.setId(id)

    }

    @SuppressLint("Range")
    fun signIn(email: String, password: String): User? {
        var query = "SELECT * " +
                "FROM ${Requetes.TABLE_TEMPORARYWORKERS} "+
                " WHERE ${Requetes.COL_EMAIL_TEMPORARYWORKER} = ? AND " +
                "${Requetes.COL_PASSWORD_TEMPORARYWORKER} = ?"

        val selectionArgs = arrayOf(email, password)
        var cursor = db.rawQuery(query, selectionArgs)
        var user : User? = null

        if(cursor.moveToFirst()){
            user = cursorToWorker(cursor)
            cursor.close()
        }else{
            query = "SELECT * " +
                    "FROM ${Requetes.TABLE_EMPLOYER} "+
                    " WHERE ${Requetes.COL_EMAIL_EMPLOYER} = ? AND " +
                    "${Requetes.COL_PASSWORD_EMPLOYER} = ?"

            cursor = db.rawQuery(query, selectionArgs)
            if(cursor.moveToFirst()){
                user = cursorToEmployer(cursor)
                cursor.close()
            }
        }
        Log.d("signIn", "signIn: $user")
        return user
    }

    @SuppressLint("Range")
    fun getTemporaryWorker(TEMPORARYWORKER_id: Long): TemporaryWorker? {
        val query = "SELECT * FROM ${Requetes.TABLE_TEMPORARYWORKERS} WHERE ${Requetes.COL_ID_TEMPORARYWORKER} = '$TEMPORARYWORKER_id';"
        val cursor = db.rawQuery(query, null)
        var temporaryWorker: TemporaryWorker? = null

        if(cursor.moveToFirst()){
            temporaryWorker = cursorToWorker(cursor)
        }
        cursor.close()
        Log.d("getTemporaryWorker", temporaryWorker.toString())
        return temporaryWorker
    }


    fun readAll(): ArrayList<TemporaryWorker> {
        val sortOrder = "${Requetes.COL_ID_TEMPORARYWORKER} DESC"
        val cursor = db.query(Requetes.TABLE_TEMPORARYWORKERS, null, null, null, null, null, sortOrder);
        val temporaryWorkers = ArrayList<TemporaryWorker>();

        while (cursor.moveToNext()) {
            temporaryWorkers.add(cursorToWorker(cursor));
        }
        cursor.close();

        return temporaryWorkers;
    }

    fun getEmployer(employerId: Long): Employer? {
        val query = "SELECT * FROM ${Requetes.TABLE_EMPLOYER} WHERE ${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(employerId.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        var employer: Employer? = null

        if(cursor.moveToFirst()){
            employer = cursorToEmployer(cursor)
        }
        cursor.close()
        return employer
    }

    private fun cursorToWorker(cursor: Cursor): TemporaryWorker {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_TEMPORARYWORKER))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_TEMPORARYWORKER))
        val lastname = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_LASTNAME_TEMPORARYWORKER))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_TEMPORARYWORKER))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_TEMPORARYWORKER))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_TEMPORARYWORKER))
        val city = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CITY_TEMPORARYWORKER))
        val birthday = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_BIRTHDAY_TEMPORARYWORKER))
        val nationality = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NATIONALITY_TEMPORARYWORKER))

        return TemporaryWorker(
            id=id,
            firstName=name,
            lastName=lastname,
            email=email,
            password=password,
            phone=phone,
            city=city,
            birthday=birthday,
            nationality=nationality
        )
    }
    private fun cursorToEmployer(cursor: Cursor): Employer? {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_EMPLOYER))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_NAME_EMPLOYER))
        val service = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SERVICE_EMPLOYER))
        val subService = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBSERVICE_EMPLOYER))
        val siret = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SIRET_EMPLOYER))
        val contact = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_CONTACT_EMPLOYER))
        val subContact = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBCONTACT_EMPLOYER))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_EMPLOYER))
        val subEmail = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_SUBEMAIL_EMPLOYER))
        val phone = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PHONE_EMPLOYER))
        val address = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_ADDRESS_EMPLOYER))
        val comment = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_COMMENTARY_EMPLOYER))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_EMPLOYER))

         return Employer(
            id=id,
            name=name,
            service=service,
            subService=subService,
            SIRET=siret,
            contact=contact,
            subContact=subContact,
            email=email,
            subEmail=subEmail,
            phone=phone,
            address=address,
            commentary=comment,
            password=password
        )
    }


    fun getUser(user_id: Long): User? {
        var user: User? = getTemporaryWorker(user_id)
        if(user == null){
            user = getEmployer(user_id)
        }
        return user
    }

    fun updateTemporaryWorker(user: TemporaryWorker) {
        val values = user.toContentValues()
        val selection = "${Requetes.COL_ID_TEMPORARYWORKER} = ?"
        val selectionArgs = arrayOf(user.getId().toString())
        db.update(Requetes.TABLE_TEMPORARYWORKERS, values, selection, selectionArgs)

    }

    fun updateEmployer(user: Employer) {
        val values = user.toContentValues()
        val selection = "${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(user.getId().toString())
        db.update(Requetes.TABLE_EMPLOYER, values, selection, selectionArgs)
    }


}