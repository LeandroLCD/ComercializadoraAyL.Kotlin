package com.leandrolcd.comercializadoraayl.login.domain

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.leandrolcd.comercializadoraayl.MainActivity
import com.leandrolcd.comercializadoraayl.core.makeNetworkCall
import com.leandrolcd.comercializadoraayl.login.data.repository.LoginRepository
import com.leandrolcd.comercializadoraayl.login.ui.LoginViewModel
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData.Companion.setLoggedInUser
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val context: Context,
    private val repository: LoginRepository
) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(email: String, password: String): UiStatus<UserData> {

        return makeNetworkCall {

            val resp = repository.doLogin(email, password)
            setLoggedInUser(resp)
            resp
        }
    }

    private fun setLoggedInUser(user: UserData) {
        val userString = Gson().toJson(user)
        context.getSharedPreferences(AUTH_PREFS, Context.MODE_PRIVATE).also {
            it.edit().putString(USER_PREFS, userString).apply()
            Log.i("setUser", userString.orEmpty())
        }

    }
    private fun getLoggedInUser(): UserData? {
        val prefs = context.getSharedPreferences(AUTH_PREFS,Context.MODE_PRIVATE) ?: return null
        val user = Gson().fromJson(prefs.getString(USER_PREFS, ""), UserData::class.java)
        if (user != null && user.idToken.isNullOrEmpty()) return null
        Log.i("getUser", prefs.getString(USER_PREFS, "")!!)
        return user
    }
    companion object {
        private const val AUTH_PREFS = "users"
        private const val USER_PREFS = "data"
    }


}