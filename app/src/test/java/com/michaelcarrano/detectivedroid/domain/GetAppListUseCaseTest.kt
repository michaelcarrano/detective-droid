package com.michaelcarrano.detectivedroid.domain

import android.content.res.Resources
import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class GetAppListUseCaseTest {

    private val resources = mock<Resources>(defaultAnswer = Mockito.RETURNS_DEEP_STUBS) {
        on { getString(android.R.string.unknownName) } doReturn "Unknown"
    }

    private lateinit var testSubject: GetAppListUseCase

    private val repo = mock<AppRepository>()
    private val mapper = AppMapper(resources)

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
            AppEntity("foo2", "bar2", "baz2", false),
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true),
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        verify(repo, times(1)).getApplications()
        testObserver.assertNoErrors()
        testObserver.assertValues(mapper.mapToUiModels(userApps))
        testObserver.dispose()
    }

    @Test
    fun `Given apps are installed, when loadApps is called with showSystemApp as true, then return list of userApps and systemApps`() {
        // GIVEN
        val showSystemApp = true
        val systemApps = listOf(
            AppEntity("foo1", "bar1", "baz1", false),
            AppEntity("foo2", "bar2", "baz2", false),
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true),
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        verify(repo, times(1)).getApplications()
        testObserver.assertNoErrors()
        testObserver.assertValues(mapper.mapToUiModels(allApps))
        testObserver.dispose()
    }

    @Test
    fun `Given apps are installed, when searchApps is called with showSystemApp as false, then return list of userApps that match query and no systemApps`() {
        // GIVEN
        val showSystemApp = false
        val query = "foo3"
        val systemApps = listOf(
            AppEntity("foo1", "bar1", "baz1", false),
            AppEntity("foo2", "bar2", "baz2", false),
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true),
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.searchApps(query, showSystemApp).test()

        // THEN
        verify(repo, times(1)).getApplications()
        testObserver.assertNoErrors()
        testObserver.assertValue(mapper.mapToUiModels(listOf(userApps.first())))
        testObserver.dispose()
    }

    @Test
    fun `Given apps are installed, when searchApps is called with showSystemApp as true, then return list of userApps and systemApps that match query`() {
        // GIVEN
        val showSystemApp = true
        val query = "foo"
        val systemApps = listOf(
            AppEntity("foo1", "bar1", "baz1", false),
            AppEntity("foo2", "bar2", "baz2", false),
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true),
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.searchApps(query, showSystemApp).test()

        // THEN
        verify(repo, times(1)).getApplications()
        testObserver.assertNoErrors()
        testObserver.assertValue(mapper.mapToUiModels(allApps))
        testObserver.dispose()
    }

    @Test
    fun `Given apps are installed, when loadApps is called with PackageManager returning null, then return list of apps`() {
        // GIVEN
        val showSystemApp = false
        val systemApps = listOf(
            AppEntity(null, null, null, false),
            AppEntity(null, "bar2", "baz2", false),
            AppEntity("foo3", null, "baz3", false),
            AppEntity("foo4", "bar4", null, false),
        )

        val userApps = listOf(
            AppEntity(null, null, null, true),
            AppEntity(null, "bar6", "baz6", true),
            AppEntity("foo7", null, "baz7", true),
            AppEntity("foo8", "bar8", null, true),
        )

        val allApps = listOf(userApps, systemApps).flatten()
        whenever(repo.getApplications()).thenReturn(Single.just(allApps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        verify(repo, times(1)).getApplications()
        testObserver.assertNoErrors()
        testObserver.assertValues(mapper.mapToUiModels(userApps))
        testObserver.dispose()
    }
}
