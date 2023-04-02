package com.leandrolcd.comercializadoraayl.login.data.network

import com.leandrolcd.comercializadoraayl.login.data.network.models.LoginDataDto
import com.leandrolcd.comercializadoraayl.login.data.network.models.LoginResponse
import com.leandrolcd.comercializadoraayl.ui.theme.KeyApplication
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface LoginClient{
    @POST
    suspend fun doLogin(@Body data:LoginDataDto, @Url uri:String = "/v1/accounts:signInWithPassword?key=$KeyApplication"): Response<LoginResponse>

}