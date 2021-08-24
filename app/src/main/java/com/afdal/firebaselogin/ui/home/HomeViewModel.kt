package com.afdal.firebaselogin.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afdal.firebaselogin.data.firebase.FirebaseService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    fun signOut(mAuth: FirebaseAuth){
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseService.signOut(mAuth)
        }

    }
}