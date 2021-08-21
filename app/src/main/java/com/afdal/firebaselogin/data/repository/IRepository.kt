package com.afdal.firebaselogin.data.repository

interface IRepository {
  //  val user :LiveData<User?>
    fun signInUser()
    fun signUpUser()
    fun signOutUser()
    fun updatePassword()
    fun updateUserData()
    fun validateUser()

}