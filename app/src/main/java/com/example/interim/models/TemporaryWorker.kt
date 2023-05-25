package com.example.interim.models

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
    private var commentary: String = ""
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

    fun getId(): Long {
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

    fun getPassword(): String {
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
}
