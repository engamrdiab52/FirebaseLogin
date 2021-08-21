package com.afdal.firebaselogin.utilis

interface IPreferenceHelper {
    fun setUserLoggedIn(loggedIn: Boolean)
    fun getUserLoggedIn(): Boolean
    fun clearPrefs()
}