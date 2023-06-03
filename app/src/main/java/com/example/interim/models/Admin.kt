package com.example.interim.models

class Admin(
    private var id: Long = 0,
    private var email: String = "",
    private var password: String = "",
    private var created_at: String = ""
) : User() {
    override fun getEmail(): String {
        return email
    }

    override fun getPhone(): String {
        return ""
    }

    override fun getPassword(): String {
        return password
    }

    override fun getRole(): String {
        return "admin"
    }

    override fun getId(): Long {
        return id
    }

    override fun getDateCreation(): String {
        return created_at
    }


}