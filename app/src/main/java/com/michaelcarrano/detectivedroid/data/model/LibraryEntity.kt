package com.michaelcarrano.detectivedroid.data.model

import java.util.Date
import java.util.UUID

data class LibraryEntity(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val source: String = "",
    val classPath: String = "",
    val createdAt: Long = Date().time,
    val updatedAt: Long = Date().time
)

val LibraryNotFoundEntity =
    LibraryEntity(
        UUID.randomUUID(),
        "NA",
        "NA",
        "NA",
        1000L,
        1000L
    )
