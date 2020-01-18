package com.michaelcarrano.detectivedroid.presentation.applist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.michaelcarrano.detectivedroid.RxTestSchedulerRule
import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: AppListViewModel

    private val idleState = State(isIdle = true)

    private val loadingState = State(isLoading = true)

    private val mapper = mock<AppMapper>()
    private val repo = mock<AppRepository>()
    private val getAppListUseCase = GetAppListUseCase(repo, mapper)

    private val observer = mock<Observer<State>>()

    @Before
    fun setUp() {
        testSubject = AppListViewModel(idleState, getAppListUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given apps successfully loaded, when action LoadApps is received, then State contains apps`() {
        // GIVEN
        val systemApps = false
        val entitys = listOf(AppEntity("foo", "bar", "baz", true))
        val uiModels = mapper.mapToUiModels(entitys)

        val successState = State(uiModels)
        whenever(repo.getApplications()).thenReturn(Single.just(entitys))
        whenever(getAppListUseCase.loadApps(systemApps)).thenReturn(Single.just(uiModels))

        // WHEN
        testSubject.dispatch(Action.LoadApps(systemApps))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given apps failed to load, when action LoadApps is received, then State contains error`() {
        // GIVEN
        val systemApps = false

        val errorState = State(isError = true)
        whenever(repo.getApplications()).thenReturn(Single.error(RuntimeException()))
        whenever(getAppListUseCase.loadApps(systemApps)).thenReturn(Single.error(RuntimeException()))

        // WHEN
        testSubject.dispatch(Action.LoadApps(systemApps))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}
