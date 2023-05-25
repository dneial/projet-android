package com.example.interim.models

class Employer(
    var id: Long = 0,
    var name: String = "",
    var service: String = "",
    var subService: String = "",
    var SIRET: String = "",
    var contact: String = "",
    var secondContact: String = "",
    var email: String = "",
    var secondEmail: String = "",
    var phone: String = "",
    var subPhone: String = "",
    var address: String = "",
    var commentary: String = "",
    var password: String = ""
) : User() {

    override fun getEmail(): String {
        return this.email
    }

    override fun getPhone(): String {
        return this.phone
    }
}