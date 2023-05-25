package com.example.interim.models

class TemporaryWorker(
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = "",
    var phone: String = "",
    var birthday: String = "",
    var nationality: String = "",
    var city: String = "",
    var commentary: String = ""
) : User() {
    override fun getEmail(): String {
        return this.email
    }

    override fun getPhone(): String {
        return this.phone
    }
}