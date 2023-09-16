package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel
import com.ww.roxie.BaseState

data class State(
    val libraries: List<LibraryUiModel> = listOf(),
    val isIdle: Boolean = false,
    val isScanning: Boolean = false,
    val isError: Boolean = false,
) : BaseState {
    val isEmpty: Boolean
        get() = libraries.isEmpty()
}
