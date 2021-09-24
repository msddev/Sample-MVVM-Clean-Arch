package com.mkdev.zerotohero.di

import android.content.Context
import com.mkdev.zerotohero.presentation.utils.PresentationPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Singleton
    @Provides
    fun providePresentationPreferenceHelper(
        @ApplicationContext context: Context
    ): PresentationPreferencesHelper = PresentationPreferencesHelper(context)

}