package com.example.local.di

import android.content.Context
import androidx.room.Room
import com.example.local.root.AppDatabase
import com.example.local.dao.SatelliteDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        context = app,
        klass = AppDatabase::class.java,
        name = "satellite_db"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDao(appDatabase: AppDatabase): SatelliteDao {
        return appDatabase.satelliteDao()
    }


}