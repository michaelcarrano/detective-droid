package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase
import com.nhaarman.mockito_kotlin.mock
import org.junit.Before

class AppDetailViewModelTest {

    private lateinit var testSubject: AppDetailViewModel

    private val scanAppUseCase = mock<ScanAppUseCase>()

    @Before
    fun setUp() {
        testSubject = AppDetailViewModel(null, scanAppUseCase)
    }
}
