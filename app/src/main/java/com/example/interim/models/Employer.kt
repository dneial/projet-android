package com.example.interim.models

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

    fun getId(): Long {
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

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}