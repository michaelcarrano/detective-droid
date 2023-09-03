package com.michaelcarrano.detectivedroid.presentation.appdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase

class AppDetailViewModelFactory(
    private val initialState: State?,
    private val scanAppUseCase: ScanAppUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppDetailViewModel(initialState, scanAppUseCase) as T
    }
}
