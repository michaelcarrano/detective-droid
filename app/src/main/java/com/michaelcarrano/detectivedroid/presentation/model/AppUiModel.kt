package com.michaelcarrano.detectivedroid.presentation.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AppUiModel(
    val name: String,
    val packageName: String,
    val versionName: String
) : Parcelable
