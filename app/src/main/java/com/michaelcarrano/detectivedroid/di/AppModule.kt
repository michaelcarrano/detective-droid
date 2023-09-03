package com.michaelcarrano.detectivedroid.di

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providePackageManager(@ApplicationContext context: Context) = context.packageManager

    @Provides
    fun provideResources(@ApplicationContext context: Context) = context.resources

    @Provides
    fun providePreferences(@ApplicationContext context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)
}
