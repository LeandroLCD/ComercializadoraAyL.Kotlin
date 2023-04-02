package com.leandrolcd.comercializadoraayl.login.domain

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.leandrolcd.comercializadoraayl.login.data.repository.LoginRepository
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus
import javax.inject.Inject

class AuthLoginUseCase @Inject constructor(
                       private val repository: LoginRepository) {
    suspend operator fun invoke(email: String, password: String): UiStatus<Any> {
        return repository.authLogin(email, password)

    }
    fun getUser():FirebaseUser?{
        return repository.getUser()
    }

}