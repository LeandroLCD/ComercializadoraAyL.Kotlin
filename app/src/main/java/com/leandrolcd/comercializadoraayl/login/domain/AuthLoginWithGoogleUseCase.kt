package com.leandrolcd.comercializadoraayl.login.domain

import com.leandrolcd.comercializadoraayl.login.data.repository.LoginRepository
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

class AuthLoginWithGoogleUseCase @Inject constructor(
    private val repository: LoginRepository) {

    suspend operator fun invoke(idToken: String): UiStatus<Any> = repository.authLoginWithGoogle(idToken)


}