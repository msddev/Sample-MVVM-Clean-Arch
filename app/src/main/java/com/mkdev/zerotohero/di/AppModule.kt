package com.mkdev.zerotohero.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mkdev.zerotohero.R
import com.mkdev.zerotohero.core.theme.ThemeUtils
import com.mkdev.zerotohero.core.theme.ThemeUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideThemeUtils(themeUtilsImpl: ThemeUtilsImpl): ThemeUtils = themeUtilsImpl

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
        )
}