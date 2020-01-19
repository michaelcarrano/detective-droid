package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel
import com.ww.roxie.BaseState

data class State(
    val apps: List<AppUiModel> = listOf(),
    val isIdle: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : BaseState
