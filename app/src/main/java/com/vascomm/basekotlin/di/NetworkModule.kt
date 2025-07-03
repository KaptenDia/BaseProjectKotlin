package com.vascomm.basekotlin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.vascomm.basekotlin.BuildConfig
import com.vascomm.basekotlin.data.remote.service.AuthService
import com.vascomm.basekotlin.data.remote.service.UserService
import com.vascomm.basekotlin.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @LoginUrl
    fun provideBaseUrlLogin() = Constants.LOGIN_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(ChuckerInterceptor(context))
                .build()
        } else {
            OkHttpClient.Builder().build()
        }

    @Singleton
    @Provides
    @BaseRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl url: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    @LoginRetrofit
    fun provideRetrofitLogin(okHttpClient: OkHttpClient, @LoginUrl url: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUserService(@BaseRetrofit retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideAuthService(@LoginRetrofit retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LoginUrl

