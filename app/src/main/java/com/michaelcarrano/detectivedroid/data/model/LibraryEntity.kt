package com.michaelcarrano.detectivedroid.data.model

import java.util.UUID

data class LibraryEntity(
    val id: UUID,
    val name: String,
    val source: String,
    val classPath: String,
    val createdAt: Long,
    val updatedAt: Long
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
