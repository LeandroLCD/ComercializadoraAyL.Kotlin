package com.leandrolcd.comercializadoraayl.login.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseUser
import com.leandrolcd.comercializadoraayl.login.data.network.LoginService
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import java.time.LocalDateTime
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: LoginService) {

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun doLogin(user: String, password: String): UserData {
        val response = api.doLogin(user, password)
        return UserData(
            email = response.email,
            expiresIn = response.expiresIn,
            idToken = response.idToken,
            refreshToken = response.refreshToken,
            localId = response.localId,
            dateLogin = LocalDateTime.now()
        )
    }

    suspend fun authLogin(user: String, password: String): UiStatus<Any> =
        suspendCancellableCoroutine { continuation ->
            api.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("authLogin", "authLogin: El usuario ha iniciado sesión con éxito")
                        val userD = task.result?.user
                        continuation.resume(UiStatus.Success(userD!!), null)
                    } else {
                        Log.d("authLogin", "Error al iniciar sesión")
                        continuation.resume(UiStatus.Error(task.exception?.message), null)
                    }
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }

        }

    fun getUser():FirebaseUser?{
      return  api.getUser()
    }
    suspend fun authLoginWithGoogle(idToken: String): UiStatus<Any> =
        suspendCancellableCoroutine { continuation ->
            api.signInWithGoogle(idToken)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("authLogin", "authLoginWithGoogle: El usuario ha iniciado sesión con éxito")
                        val userD = task.result?.user
                        continuation.resume(UiStatus.Success(userD!!), null)
                    } else {
                        Log.d("authLogin", "Error al iniciar sesión con Google")
                        continuation.resume(UiStatus.Error(task.exception?.message), null)
                    }
                }
                .addOnCanceledListener {
                    continuation.cancel()
                }
        }

}