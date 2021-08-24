package com.afdal.firebaselogin.data.firebase

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdal.firebaselogin.MainActivity
import com.afdal.firebaselogin.MainActivity.Companion.TAG
import com.afdal.firebaselogin.data.repository.User
import com.afdal.firebaselogin.ui.loginFlow.login.FirebaseResponseStatus
import com.afdal.firebaselogin.utilis.SingleLiveEvent
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

object FirebaseService {
    var _firebaseUser = SingleLiveEvent<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> get() = _firebaseUser

    val _responseOfFirebase = MutableLiveData<User>()
    val responseOfFirebase: LiveData<User>
        get() = _responseOfFirebase
    private val _status = MutableLiveData(FirebaseResponseStatus.DONE)
    val status: LiveData<FirebaseResponseStatus>
        get() = _status

    /* fun signIn(activity: Activity, mAuth: FirebaseAuth, email: String, password: String){
         mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
             if (task.isSuccessful) {
                 Log.e(TAG, "signIn: Success!")
                 val user = mAuth.getCurrentUser()
                 Log.d(TAG, user.toString())
                 _firebaseUser.value = user
             } else {
                 Log.e(TAG, "signIn: Fail!", task.exception)
                 _firebaseUser.value = null
             }
         }
     }*/

    suspend fun signIn(activity: Activity, mAuth: FirebaseAuth, email: String, password: String) {
        try {
            val authResult: AuthResult? = mAuth.signInWithEmailAndPassword(email, password).await()
            _firebaseUser.postValue(authResult?.user)
            _status.postValue(FirebaseResponseStatus.DONE)
            Log.d(TAG, authResult?.user?.email.toString())
        }catch (e : Exception){
            _status.postValue(FirebaseResponseStatus.ERROR)
            Log.d(TAG, e.message.toString())
        }

    }
     fun signOut( mAuth: FirebaseAuth) {
        try {
           mAuth.signOut()
            _status.postValue(FirebaseResponseStatus.DONE)
            Log.d(MainActivity.TAG,"After : "+ mAuth.currentUser?.email.toString())
            Log.d(TAG, "USER SIGNED OUT")
        }catch (e : Exception){
            _status.postValue(FirebaseResponseStatus.ERROR)
            Log.d(TAG, e.message.toString())
        }

    }

   suspend fun signUp( mAuth: FirebaseAuth, email: String, password: String) {
       try {
           val authResult: AuthResult? = mAuth.createUserWithEmailAndPassword(email, password).await()
           _firebaseUser.postValue(authResult?.user)
           _status.postValue(FirebaseResponseStatus.DONE)
           Log.d(TAG, authResult?.user?.email.toString())
       }catch (e : Exception){
           _status.postValue(FirebaseResponseStatus.ERROR)
           Log.d(TAG, e.message.toString())
       }

    }

}