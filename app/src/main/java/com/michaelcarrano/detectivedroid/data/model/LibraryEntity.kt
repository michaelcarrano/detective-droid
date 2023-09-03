package com.michaelcarrano.detectivedroid.data.model

import kotlin.random.Random

data class LibraryEntity(
    val id: Int,
    val name: String,
    val source: String,
    val classPath: String,
)

val LibraryNotFoundEntity =
    LibraryEntity(
        Random.nextInt(),
        "NA",
        "NA",
        "NA",
    )
