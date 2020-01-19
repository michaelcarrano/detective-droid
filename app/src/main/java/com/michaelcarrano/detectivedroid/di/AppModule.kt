package com.michaelcarrano.detectivedroid.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.michaelcarrano.detectivedroid.BaseApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: BaseApplication): Context = application.applicationContext

    @Provides
    fun providePackageManager(context: Context) = context.packageManager

    @Provides
    fun provideResources(context: Context) = context.resources

    @Provides
    fun providePreferences(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)
}
