package com.vascomm.basekotlin.di

import com.vascomm.basekotlin.data.remote.source.UserDataSourceImpl
import com.vascomm.basekotlin.data.repository.UserDataSource
import com.vascomm.basekotlin.data.repository.UserRepositoryImpl
import com.vascomm.basekotlin.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Main [Module] that holds all repository classes and provides these instances
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideUserDataSourceImpl(userDataSource: UserDataSourceImpl): UserDataSource =
        userDataSource

    @Provides
    @Singleton
    fun provideUserRepository(userRepository: UserRepositoryImpl): UserRepository =
        userRepository
}