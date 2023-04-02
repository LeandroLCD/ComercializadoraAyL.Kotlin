package com.leandrolcd.comercializadoraayl.login.data.network.models


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: Error
)