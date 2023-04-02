package com.leandrolcd.comercializadoraayl.login.data.network.models


import com.google.gson.annotations.SerializedName

data class ErrorX(
    @SerializedName("domain")
    val domain: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("reason")
    val reason: String
)