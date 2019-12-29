package com.michaelcarrano.detectivedroid.data

import android.content.pm.PackageManager
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before
import org.junit.Test

class AppRepositoryImplTest {

    private lateinit var testSubject: AppRepositoryImpl
    private val packageManager = mock<PackageManager>()

    @Before
    fun setUp() {
        testSubject = AppRepositoryImpl(packageManager)
    }

    @Test
    fun `Given apps are installed, when calling getApplications(), then return list of applications`() {
    }

    @Test
    fun `Given apps are installed and showSystemApp is true, when calling getApplications(), then return list of all applications`() {
    }

    @Test
    fun `Given apps are installed and showSystemApp is false, when calling getApplications(), then return list without system applications`() {
    }
}
