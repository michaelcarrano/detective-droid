package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import java.util.Date
import java.util.UUID
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LibraryMapperTest {

    private lateinit var testSubject: LibraryMapper

    @Before
    fun setUp() {
        testSubject = LibraryMapper()
    }

    @Test
    fun `Given LibraryEntity, When mapping to LibraryUiModel, Then return valid LibraryUiModel`() {
        val libraryEntity = LibraryEntity(
            UUID.randomUUID(),
            "foo",
            "bar",
            "baz",
            Date().time,
            Date().time
        )
        val libraryUiModel = testSubject.mapToUiModel(libraryEntity)

        assertEquals(libraryEntity.name, libraryUiModel.name)
        assertEquals(libraryEntity.source, libraryUiModel.source)
        assertEquals(libraryEntity.classPath, libraryUiModel.classPath)

        // 3 fields + Parcelable.CREATOR
        assertEquals(4, libraryUiModel.javaClass.declaredFields.size)
    }
}
