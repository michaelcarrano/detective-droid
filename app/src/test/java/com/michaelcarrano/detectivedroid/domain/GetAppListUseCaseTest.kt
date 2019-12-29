package com.michaelcarrano.detectivedroid.domain

import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
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
    fun `Given apps are installed, when loadApps is called with showSystemApp as false, then return list of apps`() {
        // GIVEN
        val showSystemApp = false
        val apps = listOf(AppEntity("foo", "bar", "baz"))
        whenever(repo.getApplications(showSystemApp)).thenReturn(Single.just(apps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(mapper.mapToUiModel(apps.first())))
    }

    @Test
    fun `Given apps are installed, when loadApps is called with showSystemApp as true, then return list of apps`() {
        // GIVEN
        val showSystemApp = true
        val apps = listOf(AppEntity("foo", "bar", "baz"))
        whenever(repo.getApplications(showSystemApp)).thenReturn(Single.just(apps))

        // WHEN
        val testObserver = testSubject.loadApps(showSystemApp).test()

        // THEN
        testObserver.assertNoErrors()
        testObserver.assertValue(listOf(mapper.mapToUiModel(apps.first())))
    }
}
