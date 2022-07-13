package com.obvious.nasapics.di

import android.content.Context
import androidx.room.Room
import com.obvious.nasapics.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {
    @Provides
    @Singleton
    fun providesRoomDB(@ApplicationContext context: Context)=
        Room.databaseBuilder(context,
            AppDatabase::class.java, "app_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesImageResultDao(appDatabase: AppDatabase)=
        appDatabase.imageResultDao()
}