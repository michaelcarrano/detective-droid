package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel

sealed class Change {
    object Scanning : Change()
    object NoLibrariesFound : Change()
    data class Libraries(val libraries: List<LibraryUiModel>) : Change()
    data class Error(val throwable: Throwable?) : Change()
}
