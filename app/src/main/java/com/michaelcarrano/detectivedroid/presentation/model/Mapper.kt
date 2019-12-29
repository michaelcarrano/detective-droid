package com.michaelcarrano.detectivedroid.presentation.model

interface Mapper<out V, in D> {

    fun mapToUiModel(type: D): V
}
