package com.mkdev.zerotohero.di

import android.content.Context
import com.mkdev.zerotohero.cache.datasourceImpl.CharacterCacheImpl
import com.mkdev.zerotohero.cache.dao.CharacterDao
import com.mkdev.zerotohero.cache.database.CharactersDatabase
import com.mkdev.zerotohero.cache.utils.CachePreferencesHelper
import com.mkdev.zerotohero.data.repository.CharacterCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): CharactersDatabase {
        return CharactersDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(charactersDatabase: CharactersDatabase): CharacterDao {
        return charactersDatabase.cachedCharacterDao()
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(@ApplicationContext context: Context): CachePreferencesHelper {
        return CachePreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideCharacterCache(characterCacheImpl: CharacterCacheImpl): CharacterCache =
        characterCacheImpl
}
