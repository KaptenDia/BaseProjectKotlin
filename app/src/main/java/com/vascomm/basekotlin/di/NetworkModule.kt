package com.vascomm.basekotlin.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.vascomm.basekotlin.BuildConfig
import com.vascomm.basekotlin.data.remote.ApiServices
import com.vascomm.basekotlin.data.remote.service.AuthService
import com.vascomm.basekotlin.data.remote.service.UserService
import com.vascomm.basekotlin.util.Constants
import com.vascomm.basekotlin.util.security.SSLPinningHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideCertificatePinner(@ApplicationContext context: Context): CertificatePinner {
        // TODO: Add your SSL certificate hashes here
        // Example for multiple domains:
        return SSLPinningHelper.buildCertificatePinner(
            mapOf(
                "api.github.com" to listOf(
                    "sha256/your_certificate_hash_here"
                    // Add more certificate hashes if needed (backup certificates)
                ),
                "your-api-domain.com" to listOf(
                    "sha256/your_certificate_hash_here"
                )
            )
        )

        // For now, return empty pinner (no SSL pinning)
        // Remove this and use the above when you have certificate hashes
        return CertificatePinner.Builder().build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // Add logging interceptor
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor)
                addInterceptor(ChuckerInterceptor(context))
            }

            // Add SSL Pinning (comment out if not using SSL pinning yet)
            // certificatePinner(certificatePinner)

            // Timeout configuration
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)

            // Retry configuration
            retryOnConnectionFailure(true)
        }.build()
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

    @Provides
    @Singleton
    fun provideApiServices(@LoginRetrofit retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }

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
