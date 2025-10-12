package com.michaelcarrano.detectivedroid

import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DetectiveApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
