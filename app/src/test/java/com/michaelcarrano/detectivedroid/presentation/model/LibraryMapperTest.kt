package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class LibraryMapperTest {

    private val testSubject = LibraryMapper()

    @Test
    fun `Given LibraryEntity, When mapping to LibraryUiModel, Then return valid LibraryUiModel`() {
        val libraryEntity = LibraryEntity(
            Random.nextInt(),
            "foo",
            "bar",
            "baz",
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
            LibraryEntity(Random.nextInt(), "foo1", "bar1", "baz1"),
            LibraryEntity(Random.nextInt(), "foo2", "bar2", "baz2"),
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
