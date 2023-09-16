package com.michaelcarrano.detectivedroid

import android.app.Application
import androidx.preference.PreferenceManager
import com.michaelcarrano.detectivedroid.presentation.ThemeHelper
import com.michaelcarrano.detectivedroid.presentation.settings.PREF_APP_THEME

open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val theme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString(PREF_APP_THEME, ThemeHelper.DEFAULT_MODE)!!
        ThemeHelper.applyTheme(theme)
    }
}
