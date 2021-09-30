package com.mkdev.zerotohero.di

import com.mkdev.zerotohero.BuildConfig
import com.mkdev.zerotohero.data.repository.CharacterRemote
import com.mkdev.zerotohero.remote.CharacterRemoteImpl
import com.mkdev.zerotohero.remote.api.CharacterService
import com.mkdev.zerotohero.remote.api.ServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideCharacterService(): CharacterService =
        ServiceFactory.create(BuildConfig.DEBUG, BuildConfig.BASE_URL)

    @Singleton
    @Provides
    fun provideCharacterRemote(characterRemoteImpl: CharacterRemoteImpl): CharacterRemote =
        characterRemoteImpl
}