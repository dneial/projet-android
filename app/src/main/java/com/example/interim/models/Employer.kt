package com.example.interim.models

import android.content.ContentValues
import com.example.interim.database.Requetes

class Employer(
    private var id: Long = 0,
    private var name: String = "",
    private var service: String = "",
    private var subService: String = "",
    private var SIRET: String = "",
    private var contact: String = "",
    private var subContact: String = "",
    private var email: String = "",
    private var subEmail: String = "",
    private var phone: String = "",
    private var subPhone: String = "",
    private var address: String = "",
    private var commentary: String = "",
    private var password: String = ""
) : User() {

    override fun getRole(): String {
        return "employer"
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

    // Getters and Setters for other properties

    override fun getId(): Long {
        return id
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getService(): String {
        return service
    }

    fun setService(service: String) {
        this.service = service
    }

    fun getSubService(): String {
        return subService
    }

    fun setSubService(subService: String) {
        this.subService = subService
    }

    fun getSIRET(): String {
        return SIRET
    }

    fun setSIRET(SIRET: String) {
        this.SIRET = SIRET
    }

    fun getContact(): String {
        return contact
    }

    fun setContact(contact: String) {
        this.contact = contact
    }

    fun getSubContact(): String {
        return subContact
    }

    fun setSubContact(subContact: String) {
        this.subContact = subContact
    }

    fun getSubEmail(): String {
        return subEmail
    }

    fun setSubEmail(subEmail: String) {
        this.subEmail = subEmail
    }

    fun getSubPhone(): String {
        return subPhone
    }

    fun setSubPhone(subPhone: String) {
        this.subPhone = subPhone
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(address: String) {
        this.address = address
    }

    fun getCommentary(): String {
        return commentary
    }

    fun setCommentary(commentary: String) {
        this.commentary = commentary
    }

    override fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun toContentValues(): ContentValues {
        val values = ContentValues()
        values.put(Requetes.COL_NAME_EMPLOYER, name)
        values.put(Requetes.COL_SERVICE_EMPLOYER, service)
        values.put(Requetes.COL_SUBSERVICE_EMPLOYER, subService)
        values.put(Requetes.COL_SIRET_EMPLOYER, SIRET)
        values.put(Requetes.COL_CONTACT_EMPLOYER, contact)
        values.put(Requetes.COL_SUBCONTACT_EMPLOYER, subContact)
        values.put(Requetes.COL_EMAIL_EMPLOYER, email)
        values.put(Requetes.COL_SUBEMAIL_EMPLOYER, subEmail)
        values.put(Requetes.COL_PHONE_EMPLOYER, phone)
        values.put(Requetes.COL_SUBPHONE_EMPLOYER, subPhone)
        values.put(Requetes.COL_ADDRESS_EMPLOYER, address)
        values.put(Requetes.COL_PASSWORD_EMPLOYER, password)
        values.put(Requetes.COL_COMMENTARY_EMPLOYER,commentary)
        return values
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Employer [id=").append(id)
        builder.append(", name=").append(name)
        builder.append(", service=").append(service)
        builder.append(", subService=").append(subService)
        builder.append(", SIRET=").append(SIRET)
        builder.append(", contact=").append(contact)
        builder.append(", subContact=").append(subContact)
        builder.append(", email=").append(email)
        builder.append(", subEmail=").append(subEmail)
        builder.append(", phone=").append(phone)
        builder.append(", subPhone=").append(subPhone)
        builder.append(", address=").append(address)
        builder.append(", commentary=").append(commentary)

        return builder.toString()

    }
}