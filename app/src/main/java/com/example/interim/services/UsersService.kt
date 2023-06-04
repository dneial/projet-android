package com.example.interim.services

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.interim.database.DataBase
import com.example.interim.database.Requetes
import com.example.interim.models.Admin
import com.example.interim.models.Employer
import com.example.interim.models.TemporaryWorker
import com.example.interim.models.TemporaryWorker.Companion.format_date_to_view
import com.example.interim.models.User
import java.security.MessageDigest

class UsersService() {

    var db: SQLiteDatabase = DataBase.db

    fun create(temporaryWorker: TemporaryWorker){
        Log.d("mdp", "create: ${temporaryWorker.getPassword()}")
        val values = temporaryWorker.toContentValues()
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, hashPassword(temporaryWorker.getPassword()))
        val id = db.insert(Requetes.TABLE_TEMPORARYWORKERS, null, values)
        temporaryWorker.setId(id)
    }

    fun create(employer: Employer){
        Log.d("mdp", "create: ${employer.getPassword()}")
        val values = employer.toContentValues()
        values.put(Requetes.COL_PASSWORD_EMPLOYER, hashPassword(employer.getPassword()))
        values.put(Requetes.COL_STATUS_EMPLOYER, 0)
        val id = db.insert(Requetes.TABLE_EMPLOYERS, null, values)
        employer.setId(id)
    }

    @SuppressLint("Range")
    fun signIn(email: String, password: String): User? {
        var user: User? = null
        user = getWorkerByEmailAndPassword(email, password)
        if(user == null){
            user = getEmployerByEmailAndPassword(email, password)
            if(user == null){
                user = getAdminByEmailAndPassword(email, password)
            }
        }
        return user
    }

    @SuppressLint("Range")
    fun getTemporaryWorker(TEMPORARYWORKER_id: Long): TemporaryWorker? {
        val selection = "${Requetes.COL_ID_TEMPORARYWORKER} = ?"
        val selectionArgs = arrayOf(TEMPORARYWORKER_id.toString())
        val cursor = db.query(Requetes.TABLE_TEMPORARYWORKERS, null, selection, selectionArgs, null, null, null)
        var temporaryWorker: TemporaryWorker? = null

        if(cursor.moveToFirst()){
            temporaryWorker = cursorToWorker(cursor)
        }
        cursor.close()
        return temporaryWorker
    }


    fun readAllWorkers(): ArrayList<TemporaryWorker> {
        val sortOrder = "${Requetes.COL_ID_TEMPORARYWORKER} DESC"
        val cursor = db.query(Requetes.TABLE_TEMPORARYWORKERS, null, null, null, null, null, sortOrder);
        val temporaryWorkers = ArrayList<TemporaryWorker>();

        while (cursor.moveToNext()) {
            temporaryWorkers.add(cursorToWorker(cursor));
        }
        cursor.close();

        return temporaryWorkers;
    }

    fun readAllEmployers(): ArrayList<Employer> {
        val sortOrder = "${Requetes.COL_ID_EMPLOYER} DESC"
        val cursor = db.query(Requetes.TABLE_EMPLOYERS, null, null, null, null, null, sortOrder);
        val users = ArrayList<Employer>();

        while (cursor.moveToNext()) {
            users.add(cursorToEmployer(cursor));
        }
        cursor.close();

        return users;
    }

    fun getEmployer(employerId: Long): Employer? {
        val query = "SELECT * FROM ${Requetes.TABLE_EMPLOYERS} WHERE ${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(employerId.toString())
        val cursor = db.rawQuery(query, selectionArgs)
        var employer: Employer? = null

        if(cursor.moveToFirst()){
            employer = cursorToEmployer(cursor)
        }
        cursor.close()
        return employer
    }

    fun getUser(id: Long, role: String): User? {
        when(role){
            "worker" -> {
                return getTemporaryWorker(id)!!
            }
            "employer" -> {
                return getEmployer(id)!!
            }
            else -> {
                return null
            }
        }
    }

    fun getWorkerByEmailAndPassword(email: String, password: String): TemporaryWorker? {
        val query = "SELECT * FROM ${Requetes.TABLE_TEMPORARYWORKERS} WHERE ${Requetes.COL_EMAIL_TEMPORARYWORKER} = ? AND ${Requetes.COL_PASSWORD_TEMPORARYWORKER} = ?"
        val selectionArgs = arrayOf(email, hashPassword(password))
        val cursor = db.rawQuery(query, selectionArgs)
        var worker: TemporaryWorker? = null

        if(cursor.moveToFirst()){
            worker = cursorToWorker(cursor)
        }
        cursor.close()
        return worker
    }

    fun getEmployerByEmailAndPassword(email: String, password: String): Employer? {
        val query = "SELECT * FROM ${Requetes.TABLE_EMPLOYERS} WHERE ${Requetes.COL_EMAIL_EMPLOYER} = ? AND ${Requetes.COL_PASSWORD_EMPLOYER} = ?"
        val selectionArgs = arrayOf(email, hashPassword(password))
        val cursor = db.rawQuery(query, selectionArgs)
        var employer: Employer? = null

        if(cursor.moveToFirst()){
            employer = cursorToEmployer(cursor)
        }
        cursor.close()
        return employer
    }

    fun getAdminByEmailAndPassword(email: String, password: String): Admin? {
        val query = "SELECT * FROM ${Requetes.TABLE_ADMINS} WHERE ${Requetes.COL_EMAIL_ADMIN} = ? AND ${Requetes.COL_PASSWORD_ADMIN} = ?"
        val selectionArgs = arrayOf(email, hashPassword(password))
        val cursor = db.rawQuery(query, selectionArgs)
        var admin: Admin? = null

        if(cursor.moveToFirst()){
            admin = cursorToAdmin(cursor)
        }
        cursor.close()
        return admin
    }

    private fun cursorToAdmin(cursor: Cursor): Admin {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(Requetes.COL_ID_ADMIN))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_EMAIL_ADMIN))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_PASSWORD_ADMIN))
        val created_at = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CREATION_ADMIN))

        return Admin(id=id, email=email, password=password, created_at=created_at)
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
        val created_at = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CREATION_TEMPORARYWORKER))

        return TemporaryWorker(
            id=id,
            firstName=name,
            lastName=lastname,
            email=email,
            password=password,
            phone=phone,
            city=city,
            birthday=format_date_to_view(birthday),
            nationality=nationality,
            created_at=created_at
        )
    }
    private fun cursorToEmployer(cursor: Cursor): Employer {
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
        val created_at = cursor.getString(cursor.getColumnIndexOrThrow(Requetes.COL_DATE_CREATION_EMPLOYER))

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
            password=password,
            created_at=created_at
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
        db.update(Requetes.TABLE_EMPLOYERS, values, selection, selectionArgs)
    }

    fun hashPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(password.toByteArray())
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    fun checkEmailUnique(email: String): Boolean {
        val selectionArgs = arrayOf(email)
        val cursor = db.rawQuery(Requetes.CHECK_UNIQUE_EMAIL, selectionArgs)
        return !cursor.moveToFirst()
    }


    fun checkUserConfirm(id: Long, role: String): Boolean {
        val selectionArgs = arrayOf(id.toString())
        if (role == "admin" || role == "worker"){
            return true
        } else if(db.rawQuery(Requetes.CHECK_STATUS_EMPLOYER,selectionArgs).moveToFirst()) {
            return true
        }
        return false;
    }

    fun getUnverifiedEmployers(): List<Employer> {
        val selection = "${Requetes.COL_STATUS_EMPLOYER} = ?"
        val selectionArgs = arrayOf("0")
        val cursor = db.query(
            Requetes.TABLE_EMPLOYERS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val employers = mutableListOf<Employer>()
        while (cursor.moveToNext()) {
            employers.add(cursorToEmployer(cursor))
        }
        cursor.close()
        return employers
    }

    fun verifyEmployer(id: Long) {
        val values = ContentValues()
        values.put(Requetes.COL_STATUS_EMPLOYER, 1)
        val selection = "${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(id.toString())
        db.update(Requetes.TABLE_EMPLOYERS, values, selection, selectionArgs)
    }

    fun deleteEmployer(id: Long) {
        val selection = "${Requetes.COL_ID_EMPLOYER} = ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(Requetes.TABLE_EMPLOYERS, selection, selectionArgs)
    }

    fun getUsersFrom(period: String): List<User>{
        val workers = getTemporaryWorkersFrom(period)
        val employers = getEmployersFrom(period)
        val users = mutableListOf<User>()
        users.addAll(workers)
        users.addAll(employers)
        return users
    }

    fun getEmployersFrom(period: String): List<Employer> {
        val employers = mutableListOf<Employer>()
        val selection: String
        when(period) {
            "week" -> selection = "${Requetes.COL_DATE_CREATION_EMPLOYER} BETWEEN date('now', '-7 days') AND date('now');"
            "month" -> selection = "${Requetes.COL_DATE_CREATION_EMPLOYER} BETWEEN date('now', '-1 month') AND date('now');"
            "year" -> selection = "${Requetes.COL_DATE_CREATION_EMPLOYER} BETWEEN date('now', '-1 year') AND date('now');"
            else -> selection = "${Requetes.COL_DATE_CREATION_EMPLOYER} BETWEEN date('now', '-7 days') AND date('now');"
        }

        val cursor = db.query(
            Requetes.TABLE_EMPLOYERS,
            null,
            selection,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            employers.add(cursorToEmployer(cursor))
        }

        cursor.close()
        return employers
    }

    fun getTemporaryWorkersFrom(period: String): List<TemporaryWorker> {
        val workers = mutableListOf<TemporaryWorker>()
        val selection: String = when(period) {
            "week" -> "${Requetes.COL_DATE_CREATION_TEMPORARYWORKER} BETWEEN date('now', '-7 days') AND date('now');"
            "month" -> "${Requetes.COL_DATE_CREATION_TEMPORARYWORKER} BETWEEN date('now', '-1 month') AND date('now');"
            "year" -> "${Requetes.COL_DATE_CREATION_TEMPORARYWORKER} BETWEEN date('now', '-1 year') AND date('now');"
            else -> "${Requetes.COL_DATE_CREATION_TEMPORARYWORKER} BETWEEN date('now', '-7 days') AND date('now');"
        }

        val cursor = db.query(
            Requetes.TABLE_TEMPORARYWORKERS,
            null,
            selection,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            workers.add(cursorToWorker(cursor))
        }

        cursor.close()
        return workers
    }


}
