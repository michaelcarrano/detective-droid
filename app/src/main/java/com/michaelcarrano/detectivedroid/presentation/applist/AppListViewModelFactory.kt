package com.michaelcarrano.detectivedroid.presentation.applist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase

class AppListViewModelFactory(
    private val initialState: State?,
    private val appListUseCase: GetAppListUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppListViewModel(initialState, appListUseCase) as T
    }
}
