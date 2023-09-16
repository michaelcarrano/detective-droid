package com.michaelcarrano.detectivedroid.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val packageManager: PackageManager,
) : AppRepository {

    // TODO: Investigate packageManager.getInstalledPackages() vs packageManager.getInstalledApplications()
    override fun getApplications(): Single<List<AppEntity>> {
        val apps = mutableListOf<AppEntity>()

        packageManager.getInstalledApplications(0).forEach { appInfo ->
            val userApp = appInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0 ||
                appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0

            val appEntity = AppEntity(
                packageManager.getApplicationLabel(appInfo).toString(),
                appInfo.packageName,
                packageManager.getPackageInfo(appInfo.packageName, 0).versionName,
                userApp,
            )
            apps.add(appEntity)
        }

        return Single.just(apps)
    }
}
