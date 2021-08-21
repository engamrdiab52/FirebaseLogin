package com.afdal.firebaselogin.ui.loginFlow.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    fun loginUser( email: String, password:String): Boolean {

        return true
    }

    fun updatePassword(password: String): Boolean {

        return true
    }

    fun passwordUpdated(): Boolean {
        return true
    }

    fun signUpUser( userName :String,email: String, password:String): Boolean {

        return true
    }

    fun updateProfile() {

    }

    fun verifyEmail(): Boolean {
        return true
    }

    fun validateUser() {

    }


}