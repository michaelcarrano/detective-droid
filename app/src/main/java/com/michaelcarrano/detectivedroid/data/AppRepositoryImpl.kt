package com.michaelcarrano.detectivedroid.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val packageManager: PackageManager
) : AppRepository {

    private val allAppCache = mutableListOf<AppEntity>()
    private val noSystemAppCache = mutableListOf<AppEntity>()

    // TODO: Investigate packageManager.getInstalledPackages() vs packageManager.getInstalledApplications()
    // TODO: Set this app to be first in the list
    override fun getApplications(showSystemApp: Boolean): Single<List<AppEntity>> {
        if (allAppCache.isEmpty()) {
            packageManager.getInstalledApplications(0).forEach { appInfo ->
                val appEntity = AppEntity(
                    packageManager.getApplicationLabel(appInfo).toString(),
                    appInfo.packageName,
                    packageManager.getPackageInfo(appInfo.packageName, 0).versionName
                )

                if (appInfo.flags and ApplicationInfo.FLAG_SYSTEM <= 0 ||
                    appInfo.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0
                ) {
                    noSystemAppCache.add(appEntity)
                }

                allAppCache.add(appEntity)
            }
        }

        return if (showSystemApp) Single.just(allAppCache) else Single.just(noSystemAppCache)
    }
}
