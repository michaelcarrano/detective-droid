package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel

sealed class Change {
    object Loading : Change()
    data class Apps(val apps: List<AppUiModel>) : Change()
    data class Error(val throwable: Throwable?) : Change()
}
