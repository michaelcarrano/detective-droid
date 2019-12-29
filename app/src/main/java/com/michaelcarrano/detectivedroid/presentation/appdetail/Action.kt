package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.ww.roxie.BaseAction

sealed class Action : BaseAction {
    data class ScanApp(val packageName: String) : Action()
}
