package com.michaelcarrano.detectivedroid.domain

import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetAppListUseCaseTest {

    private lateinit var testSubject: GetAppListUseCase

    private val repo = mock<AppRepository>()
    private val mapper = AppMapper()

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
            AppEntity("foo2", "bar2", "baz2", false)
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true)
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
            AppEntity("foo2", "bar2", "baz2", false)
        )

        val userApps = listOf(
            AppEntity("foo3", "bar3", "baz3", true),
            AppEntity("foo4", "bar4", "baz4", true)
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
}
