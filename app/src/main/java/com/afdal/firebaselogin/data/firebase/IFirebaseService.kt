package com.afdal.firebaselogin.data.firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdal.firebaselogin.data.repository.User

interface IFirebaseService {
    val _responseOfFirebase: MutableLiveData<User>
    val responseOfFirebase: LiveData<User>

    suspend fun signUp()
    suspend fun signOut()
    suspend fun signIn(email: String, password: String)
}