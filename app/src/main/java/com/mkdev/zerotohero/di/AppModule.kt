package com.mkdev.zerotohero.di

import com.mkdev.zerotohero.core.theme.ThemeUtils
import com.mkdev.zerotohero.core.theme.ThemeUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils = themeUtilsImpl
}