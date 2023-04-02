package com.leandrolcd.comercializadoraayl.core

import com.leandrolcd.comercializadoraayl.login.data.network.LoginClient
import com.leandrolcd.comercializadoraayl.ui.theme.BaseUrlGoogleApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrlGoogleApis)
            .addConverterFactory(GsonConverterFactory.create())
            .client(onClient())
            .build()
    }



    @Singleton
    @Provides
    fun providerLoginClient(retrofit: Retrofit):LoginClient{
        return retrofit.create(LoginClient::class.java)
    }


    private fun onClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
    }
}