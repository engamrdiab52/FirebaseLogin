package com.afdal.firebaselogin.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afdal.firebaselogin.data.firebase.FirebaseService
import com.afdal.firebaselogin.ui.loginFlow.login.FirebaseResponseStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    val firebaseUser : LiveData<FirebaseUser?> = FirebaseService.firebaseUser
    private var _status = FirebaseService.status as MutableLiveData<FirebaseResponseStatus>
    val status : LiveData<FirebaseResponseStatus>
        get() = _status

    fun signUpUser(mAuth: FirebaseAuth, email: String, password: String) {
        _status.value = FirebaseResponseStatus.LOADING
        viewModelScope.launch (Dispatchers.IO){
            FirebaseService.signUp( mAuth, email, password)
        }
    }

}
