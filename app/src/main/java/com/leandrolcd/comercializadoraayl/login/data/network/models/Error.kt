package com.leandrolcd.comercializadoraayl.login.data.network.models


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("code")
    val code: Int,
    @SerializedName("errors")
    val errors: List<ErrorX>,
    @SerializedName("message")
    val message: String
)