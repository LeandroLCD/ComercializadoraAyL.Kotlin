package com.leandrolcd.comercializadoraayl.login.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.leandrolcd.comercializadoraayl.core.makeNetworkCall
import com.leandrolcd.comercializadoraayl.login.data.network.models.ErrorResponse
import com.leandrolcd.comercializadoraayl.login.data.network.models.LoginDataDto
import com.leandrolcd.comercializadoraayl.login.data.network.models.LoginResponse
import com.leandrolcd.comercializadoraayl.login.data.network.models.UnauthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginService @Inject constructor(private val loginClient: LoginClient,
                                       private val firebaseAuth: FirebaseAuth) {


    suspend fun doLogin(user: String, password: String): LoginResponse {
        return withContext(Dispatchers.IO) {
            val jsonString = LoginDataDto(email = user, password = password)

            val response = loginClient.doLogin(data = jsonString)

            if (!response.isSuccessful) {
                val errorResponse = response.errorBody()?.let {
                    Gson().fromJson(it.string(), ErrorResponse::class.java)
                }
                if (errorResponse?.error?.code == 400) {
                    throw UnauthorizedException("El correo o contraseña no es valido")
                } else {
                    throw Exception(errorResponse?.error?.message)
                }
            }
            var result = LoginResponse(
                email = response.body()?.email.orEmpty(),
                expiresIn = response.body()?.expiresIn.orEmpty(),
                idToken = response.body()?.idToken.orEmpty(),
                refreshToken = response.body()?.refreshToken.orEmpty(),
                localId = response.body()?.localId.orEmpty()
            )

             result
        }
    }

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)

    }
    fun getUser(): FirebaseUser? {
        return  firebaseAuth.currentUser
    }
    fun signInWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return firebaseAuth.signInWithCredential(credential)
    }
    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}