package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before

class AppListViewModelTest {

    private lateinit var testSubject: AppListViewModel

    private val getAppListUseCase = mock<GetAppListUseCase>()

    @Before
    fun setUp() {
        testSubject = AppListViewModel(null, getAppListUseCase)
    }
}
