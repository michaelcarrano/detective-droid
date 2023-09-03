package com.michaelcarrano.detectivedroid.presentation.applist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.michaelcarrano.detectivedroid.RxTestSchedulerRule
import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class AppListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: AppListViewModel

    private val idleState = State(isIdle = true)

    private val loadingState = State(isLoading = true)

    private val getAppListUseCase = mock<GetAppListUseCase>()

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
        val uiModels = listOf(
            AppUiModel("foo1", "bar1", "baz1"),
            AppUiModel("foo2", "bar2", "baz2"),
        )

        val successState = State(uiModels)
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

    @Test
    fun `Given search successfully returns results, when action Search is received, then State contains apps`() {
        // GIVEN
        val systemApps = false
        val query = "foo"
        val uiModels = listOf(
            AppUiModel("foo1", "bar1", "baz1"),
            AppUiModel("foo2", "bar2", "baz2"),
        )

        val successState = State(uiModels)
        whenever(getAppListUseCase.searchApps(query, systemApps)).thenReturn(Single.just(uiModels))

        // WHEN
        testSubject.dispatch(Action.Search(query, systemApps))
        testSchedulerRule.advanceTimeBy(SEARCH_DELAY_TIME, TimeUnit.MILLISECONDS)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(successState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given search failed, when action Search is received, then State contains error`() {
        // GIVEN
        val systemApps = false
        val query = "foo"

        val errorState = State(isError = true)
        whenever(getAppListUseCase.searchApps(query, systemApps)).thenReturn(
            Single.error(
                RuntimeException(),
            ),
        )

        // WHEN
        testSubject.dispatch(Action.Search(query, systemApps))
        testSchedulerRule.advanceTimeBy(SEARCH_DELAY_TIME, TimeUnit.MILLISECONDS)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}
