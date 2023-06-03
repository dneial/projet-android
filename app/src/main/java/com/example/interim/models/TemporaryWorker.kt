package com.example.interim.models

import android.content.ContentValues
import com.example.interim.database.Requetes
import java.util.regex.Pattern

class TemporaryWorker(
    private var id: Long = 0,
    private var firstName: String = "",
    private var lastName: String = "",
    private var email: String = "",
    private var password: String = "",
    private var phone: String = "",
    private var birthday: String = "",
    private var nationality: String = "",
    private var city: String = "",
    private var commentary: String = "",
    private var created_at: String = ""
) : User() {

    override fun getRole(): String {
        return "worker"
    }

    override fun getEmail(): String {
        return email
    }

     fun setEmail(email: String) {
        this.email = email
    }

    override fun getPhone(): String {
        return phone
    }

     fun setPhone(phone: String) {
        this.phone = phone
    }

    override fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getFirstName(): String {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getBirthday(): String {
        return birthday
    }

    fun setBirthday(birthday: String) {
        this.birthday = birthday
    }

    fun getNationality(): String {
        return nationality
    }

    fun setNationality(nationality: String) {
        this.nationality = nationality
    }

    fun getCity(): String {
        return city
    }

    fun setCity(city: String) {
        this.city = city
    }

    fun getCommentary(): String {
        return commentary
    }

    fun setCommentary(commentary: String) {
        this.commentary = commentary
    }

    override fun getDateCreation(): String {
        return created_at
    }


    fun toContentValues(): ContentValues {
        var values = ContentValues()
        values.put(Requetes.COL_NAME_TEMPORARYWORKER, firstName)
        values.put(Requetes.COL_LASTNAME_TEMPORARYWORKER, lastName)
        values.put(Requetes.COL_EMAIL_TEMPORARYWORKER, email)
        values.put(Requetes.COL_PASSWORD_TEMPORARYWORKER, password)
        values.put(Requetes.COL_PHONE_TEMPORARYWORKER, phone)
        values.put(Requetes.COL_BIRTHDAY_TEMPORARYWORKER, format_date(birthday))
        values.put(Requetes.COL_CITY_TEMPORARYWORKER, city)
        values.put(Requetes.COL_NATIONALITY_TEMPORARYWORKER, nationality)
        values.put(Requetes.COL_COMMENTARY_TEMPORARYWORKER, commentary)
        return values
    }


    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Worker [id=").append(id)
        builder.append(", firstName=").append(firstName)
        builder.append(", lastName=").append(lastName)
        builder.append(", email=").append(email)
        builder.append(", password=").append(password)
        builder.append(", ville=").append(city)
        builder.append(", bday=").append(birthday)
        builder.append(", nat=").append(nationality)
        builder.append(", phone=").append(phone).append("]")
        return builder.toString()
    }

    companion object {

        fun format_date_to_view(date: String): String {
            val pattern = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})")
            val matcher = pattern.matcher(date)

            if(matcher.find()) {
                return matcher.group(3) + "/" + matcher.group(2) + "/" + matcher.group(1)
            }

            return date
        }

        fun format_date(birthday: String): String {
            val pattern = Pattern.compile("(\\d{2})/(\\d{2})/(\\d{4})")
            val matcher = pattern.matcher(birthday)

            if(matcher.find()) {
                return matcher.group(3) + "-" + matcher.group(2) + "-" + matcher.group(1)
            }

            return birthday
        }
    }

}
