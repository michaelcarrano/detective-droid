package com.michaelcarrano.detectivedroid.presentation.appdetail

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.michaelcarrano.detectivedroid.RxTestSchedulerRule
import com.michaelcarrano.detectivedroid.data.LibraryRepository
import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase
import com.michaelcarrano.detectivedroid.presentation.model.LibraryMapper
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever
import kotlin.random.Random

class AppDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: AppDetailViewModel

    private val idleState = State(isIdle = true)

    private val scanningState = State(isScanning = true)

    private val context = mock<Context>()
    private val mapper = mock<LibraryMapper>()
    private val repo = mock<LibraryRepository>()
    private val scanAppUseCase = ScanAppUseCase(context, repo, mapper)

    private val observer = mock<Observer<State>>()

    @Before
    fun setUp() {
        testSubject = AppDetailViewModel(idleState, scanAppUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given libraries successfully scanned, when action ScanApps is received, then State contains libraries`() {
        // GIVEN
        val packageName = "foo"
        val entitys = listOf(
            LibraryEntity(Random.nextInt(), "name", "source", "classPath"),
        )
        val uiModels = mapper.mapToUiModels(entitys)

        val successState = State(uiModels)
        whenever(repo.getLibraries()).thenReturn(Single.just(entitys))
        whenever(scanAppUseCase.scanApp(packageName)).thenReturn(Single.just(uiModels))

        // WHEN
        testSubject.dispatch(Action.ScanApp(packageName))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(scanningState)
            verify(observer).onChanged(successState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given libraries successfully scanned and no libraries were detected, when action ScanApps is received, then State contains no libraries`() {
        // GIVEN
        val packageName = "foo"

        val emptyState = State(emptyList())
        whenever(repo.getLibraries()).thenReturn(Single.just(emptyList()))
        whenever(scanAppUseCase.scanApp(packageName)).thenReturn(Single.just(emptyList()))

        // WHEN
        testSubject.dispatch(Action.ScanApp(packageName))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(scanningState)
            verify(observer).onChanged(emptyState)
        }
        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given libraries failed to scanned, when action ScanApps is received, then State contains error`() {
        // GIVEN
        val packageName = "foo"
        val errorState = State(isError = true)
        whenever(repo.getLibraries()).thenReturn(Single.error(RuntimeException()))
        whenever(scanAppUseCase.scanApp(packageName)).thenReturn(Single.error(RuntimeException()))

        // WHEN
        testSubject.dispatch(Action.ScanApp(packageName))
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(scanningState)
            verify(observer).onChanged(errorState)
        }
        verifyNoMoreInteractions(observer)
    }
}
