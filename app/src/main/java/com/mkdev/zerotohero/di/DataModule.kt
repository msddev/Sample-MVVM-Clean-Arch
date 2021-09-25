package com.mkdev.zerotohero.di

import com.mkdev.zerotohero.BuildConfig
import com.mkdev.zerotohero.data.repositoryImpl.SettingsRepositoryImpl
import com.mkdev.zerotohero.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideSettingsRepository(): SettingsRepository =
        SettingsRepositoryImpl(BuildConfig.VERSION_NAME)
}