package com.leandrolcd.comercializadoraayl.login.data.network.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email") val email: String,
    @SerializedName("expiresIn")val expiresIn: String,
    @SerializedName("idToken")val idToken: String,
    @SerializedName("localId")val localId: String,
    @SerializedName("refreshToken")val refreshToken: String
)
