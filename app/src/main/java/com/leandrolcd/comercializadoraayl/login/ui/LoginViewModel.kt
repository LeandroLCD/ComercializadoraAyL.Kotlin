package com.leandrolcd.comercializadoraayl.login.ui

import android.content.Context
import android.os.Build
import android.util.Log
import android.util.Patterns
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseOptions
import com.leandrolcd.comercializadoraayl.login.domain.AuthLoginUseCase
import com.leandrolcd.comercializadoraayl.login.domain.AuthLoginWithGoogleUseCase
import com.leandrolcd.comercializadoraayl.login.domain.LoginUseCase
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus
import com.leandrolcd.comercializadoraayl.ui.theme.KeyApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authLoginWithGoogleUseCase: AuthLoginWithGoogleUseCase,
    private val authLoginUseCase: AuthLoginUseCase,
    private val context: Context): ViewModel() {
    init {
        var user = authLoginUseCase.getUser()
        if(user != null){
            Log.d("log", "Usuario id: ${user.uid}, name ${user.displayName}")
            //navega a la app
        }
    }

    //region Fields
    private var googleToken: String? = null
    val googleAppId = FirebaseOptions.fromResource(context)?.apiKey
    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _isLoginEnabled = MutableLiveData<Boolean>()
    private val _uiStatus = MutableLiveData<UiStatus<UserData>>()
    //endregion

    //region Properties
    val email : LiveData<String> = _email
    val password : LiveData<String> = _password
    val isLoginEnabled : LiveData<Boolean> = _isLoginEnabled
    val uiStatus : LiveData<UiStatus<UserData>> = _uiStatus
    //endregion

    //region Methods
    fun onLoginChange(email:String, pwd:String){
        _email.value = email
        _password.value = pwd
        _isLoginEnabled.value = enabledLogin(email = email, password = pwd)
    }
    fun setGoogleToken(token: String) {
        googleToken = token
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onLoginClicked(){
        viewModelScope.launch {

            _uiStatus.value = authLoginUseCase.invoke(email.value!!, password.value!!) as UiStatus<UserData>

            }
    }
    fun onLoginWithGoogleClicked(idToken: String) {
        viewModelScope.launch {
            _uiStatus.value = authLoginWithGoogleUseCase.invoke(idToken) as UiStatus<UserData>
            Log.d("userAut", "onLoginWithGoogleClicked: ${uiStatus.value.toString()}")
        }
    }




    private fun enabledLogin(email:String, password:String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5
    //region



}