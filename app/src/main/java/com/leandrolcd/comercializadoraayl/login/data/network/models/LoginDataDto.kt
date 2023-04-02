package com.leandrolcd.comercializadoraayl.login.data.network.models

import com.google.gson.annotations.SerializedName

data class LoginDataDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("returnSecureToken")
    val returnSecureToken: Boolean = true
)
