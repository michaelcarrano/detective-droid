package com.michaelcarrano.detectivedroid

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import timber.log.Timber

class DetectiveApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(
                SharedPreferencesFlipperPlugin(
                    this,
                    "${packageName}_preferences" // Same as PreferenceManager.getDefaultSharedPreferencesName(context) which is a private method
                )
            )
            client.addPlugin(CrashReporterPlugin.getInstance())
            client.start()
        }

        Timber.plant(Timber.DebugTree())
    }
}
