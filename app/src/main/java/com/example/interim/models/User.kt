package com.example.interim.models

open abstract class User() {

    abstract fun getEmail(): String
    abstract fun getPhone(): String
    abstract fun getPassword(): String
    abstract fun getRole(): String

    abstract fun getId(): Long
}