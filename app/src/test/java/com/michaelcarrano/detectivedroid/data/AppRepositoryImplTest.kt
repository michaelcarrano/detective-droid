package com.michaelcarrano.detectivedroid.data

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before

class AppRepositoryImplTest {

    private lateinit var testSubject: AppRepositoryImpl
    private val packageManager = mock<PackageManager>()
    private val applicationInfo = mock<ApplicationInfo>()

    @Before
    fun setUp() {
        testSubject = AppRepositoryImpl(packageManager)
    }

    // @Test TODO: Fix NPE for packageName
    fun `Given apps are installed, when calling getApplications(), then return list of applications`() {
        // GIVEN
        whenever(packageManager.getInstalledApplications(any())).thenReturn(
            mutableListOf(
                applicationInfo
            )
        )
        whenever(packageManager.getApplicationLabel(applicationInfo)).thenReturn("foo")
        whenever(packageManager.getApplicationInfo(any(), any()).packageName).thenReturn("bar")

        // WHEN
        val testObserver = testSubject.getApplications().test()

        // THEN
        testObserver.assertNoErrors()
    }
}
