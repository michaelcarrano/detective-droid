package com.michaelcarrano.detectivedroid

import androidx.preference.PreferenceManager
import com.michaelcarrano.detectivedroid.di.DaggerAppComponent
import com.michaelcarrano.detectivedroid.presentation.ThemeHelper
import com.michaelcarrano.detectivedroid.presentation.settings.PREF_APP_THEME
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

abstract class BaseApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        val theme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(PREF_APP_THEME, ThemeHelper.DEFAULT_MODE)!!
        ThemeHelper.applyTheme(theme)
    }

    override fun applicationInjector(): AndroidInjector<BaseApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}
