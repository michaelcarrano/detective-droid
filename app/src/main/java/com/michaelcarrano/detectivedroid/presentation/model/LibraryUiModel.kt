package com.michaelcarrano.detectivedroid.presentation.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class LibraryUiModel(
    val name: String,
    val source: String,
    val classPath: String,
) : Parcelable
