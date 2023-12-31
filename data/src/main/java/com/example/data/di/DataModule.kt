package com.example.data.di

import android.content.Context
import com.example.common.provider.SatelliteSource
import com.example.common.util.ProviderUtil
import com.example.data.repository.SatelliteRepository
import com.example.data.repository.SatelliteRepositoryImpl
import com.example.local.dao.SatelliteDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUtil(@ApplicationContext context: Context, moshi: Moshi): ProviderUtil {
        return ProviderUtil(context, moshi)
    }

    @Provides
    @Singleton
    fun provideSatelliteSource(providerUtil: ProviderUtil): SatelliteSource {
        return SatelliteSource(providerUtil)
    }

    @Provides
    @Singleton
    fun provideSatelliteRepository(
        localDataSource: SatelliteDao,
        satelliteSource: SatelliteSource
    ): SatelliteRepository {
        return SatelliteRepositoryImpl(satelliteSource, localDataSource)
    }
}
