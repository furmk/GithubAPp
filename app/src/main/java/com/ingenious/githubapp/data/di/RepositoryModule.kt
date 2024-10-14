package com.ingenious.githubapp.data.di

import com.ingenious.githubapp.data.repository.UserRepositoryImpl
import com.ingenious.githubapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}