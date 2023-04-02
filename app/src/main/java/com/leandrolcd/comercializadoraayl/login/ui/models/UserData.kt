package com.leandrolcd.comercializadoraayl.login.ui.models

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.time.LocalDateTime

class UserData (
    val email: String,
    val expiresIn: String,
    val idToken: String,
    val localId: String,
    val refreshToken: String,
    val dateLogin: LocalDateTime
) {
    companion object {
        private const val AUTH_PREFS = "users"
        private const val USER_PREFS = "data"
        fun setLoggedInUser(context: Context, user: UserData) {
            val userString = Gson().toJson(user)
            context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
                it.edit().putString(USER_PREFS, userString).apply()
                Log.i("setUser", userString.orEmpty())
            }

        }


        fun logout(context: Context) {
            context.getSharedPreferences(
                AUTH_PREFS,
                Context.MODE_PRIVATE
            ).also {
                it.edit().clear().apply()
            }
        }
    }
}