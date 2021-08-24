package com.afdal.firebaselogin.ui.loginFlow.login

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afdal.firebaselogin.data.firebase.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var loggedIn: Boolean = false

    private var _status = FirebaseService.status as MutableLiveData<FirebaseResponseStatus>
    val status : LiveData<FirebaseResponseStatus>
        get() = _status
    val firebaseUser : LiveData<FirebaseUser?> = FirebaseService.firebaseUser
    fun signIn(activity: Activity, mAuth: FirebaseAuth, email: String, password: String) {
        _status.value = FirebaseResponseStatus.LOADING
        viewModelScope.launch (Dispatchers.IO){
            FirebaseService.signIn(activity, mAuth, email, password)
        }
    }


    fun updatePassword(password: String): Boolean {

        return true
    }

    fun passwordUpdated(): Boolean {
        return true
    }

    fun signUpUser(userName: String, email: String, password: String): Boolean {

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
enum class FirebaseResponseStatus {
    LOADING, ERROR, DONE
}