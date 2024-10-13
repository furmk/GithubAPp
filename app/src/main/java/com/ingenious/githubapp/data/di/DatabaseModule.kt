package com.ingenious.githubapp.data.di

import android.content.Context
import androidx.room.Room
import com.ingenious.githubapp.data.source.local.UserDao
import com.ingenious.githubapp.data.source.local.UserDetailsDao
import com.ingenious.githubapp.data.source.local.UsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): UsersDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = UsersDatabase::class.java,
            name = "database"
        ).build()

    @Provides
    fun provideGithubUserDao(db: UsersDatabase): UserDao = db.userDao()

    @Provides
    fun provideGithubUserDetailsDao(db: UsersDatabase): UserDetailsDao =
        db.userDetailsDao()
}