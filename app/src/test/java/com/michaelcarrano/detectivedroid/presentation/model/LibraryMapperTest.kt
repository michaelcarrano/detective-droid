package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import java.util.Date
import java.util.UUID
import org.junit.Assert.assertEquals
import org.junit.Test

class LibraryMapperTest {

    private val testSubject = LibraryMapper()

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
        // assertEquals(4, libraryUiModel.javaClass.declaredFields.size)
    }

    @Test
    fun `Given LibraryEntitys, When mapping to LibraryUiModels, Then return valid LibraryUiModels`() {
        val libraryEntitys = listOf(
            LibraryEntity(UUID.randomUUID(), "foo1", "bar1", "baz1", Date().time, Date().time),
            LibraryEntity(UUID.randomUUID(), "foo2", "bar2", "baz2", Date().time, Date().time)
        )
        val libraryUiModels = testSubject.mapToUiModels(libraryEntitys)

        assertEquals(libraryEntitys.size, libraryUiModels.size)

        for (i in 0 until libraryEntitys.count()) {
            assertEquals(libraryEntitys[i].name, libraryUiModels[i].name)
            assertEquals(libraryEntitys[i].source, libraryUiModels[i].source)
            assertEquals(libraryEntitys[i].classPath, libraryUiModels[i].classPath)
        }
    }
}
