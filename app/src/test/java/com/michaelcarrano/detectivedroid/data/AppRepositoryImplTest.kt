package com.michaelcarrano.detectivedroid.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

class AppRepositoryImplTest {

    private lateinit var testSubject: AppRepositoryImpl
    private val packageManager = mock<PackageManager>()

    @Before
    fun setUp() {
        testSubject = AppRepositoryImpl(packageManager)
    }

    @Test
    fun `Given apps are installed, when calling getApplications(), then return list of applications`() {
        // GIVEN
        val installedApp = AppEntity("foo", "bar", "1", true)
        val appInfo = ApplicationInfo()
        appInfo.packageName = installedApp.packageName
        whenever(packageManager.getInstalledApplications(0)).thenReturn(listOf(appInfo))

        val pkgInfo = PackageInfo()
        pkgInfo.versionName = installedApp.versionName
        whenever(
            packageManager.getPackageInfo(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(pkgInfo)
        whenever(packageManager.getApplicationLabel(appInfo)).thenReturn(installedApp.name)

        // WHEN
        val testObserver = testSubject.getApplications().test()

        // THEN
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(installedApp))
    }
}
