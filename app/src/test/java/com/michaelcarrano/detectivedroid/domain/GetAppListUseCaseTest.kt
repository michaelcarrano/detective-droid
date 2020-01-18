package com.michaelcarrano.detectivedroid.domain

import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetAppListUseCaseTest {

    private lateinit var testSubject: GetAppListUseCase

    private val repo = mock<AppRepository>()
    private val mapper = mock<AppMapper>()

    @Before
    fun setUp() {
        testSubject = GetAppListUseCase(repo, mapper)
    }

    @Test
    fun `Given apps are installed, when loadApps is called with showSystemApp as false, then return list of userApps and no systemApps`() {
        // GIVEN
        val showSystemApp = false
        val systemApps = listOf(
            AppEntity("foo1", "bar1", "baz1", false),
            AppEntity("foo2", "bar2", "baz2", false)
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true)
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        testObserver.assertNoErrors()
        assertNotNull(testObserver.values())
        // TODO: Add additional validation logic
    }

    @Test
    fun `Given apps are installed, when loadApps is called with showSystemApp as true, then return list of userApps and systemApps`() {
        // GIVEN
        val showSystemApp = true
        val systemApps = listOf(
            AppEntity("foo1", "bar1", "baz1", false),
            AppEntity("foo2", "bar2", "baz2", false)
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true)
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        testObserver.assertNoErrors()
        assertNotNull(testObserver.values())
        // TODO: Add additional validation logic
    }
}
