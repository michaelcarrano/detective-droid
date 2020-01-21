package com.michaelcarrano.detectivedroid.presentation.applist

import com.ww.roxie.BaseAction

sealed class Action : BaseAction {
    data class LoadApps(val showSystemApp: Boolean = false) : Action()
    data class Search(val query: String?, val showSystemApp: Boolean = false) : Action()
}
