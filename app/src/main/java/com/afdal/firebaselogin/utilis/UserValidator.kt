package com.afdal.firebaselogin.utilis

class UserValidator(private val email: String, private val password: String) {

    val isUserLofggedIn: Boolean
    get() = validateCredentials()

    private fun validateCredentials(): Boolean {
        return email == "a" && password == "a"

    }
}