package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.AppEntity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class AppMapperTest {

    private lateinit var testSubject: AppMapper

    @Before
    fun setUp() {
        testSubject = AppMapper()
    }

    @Test
    fun `Given AppEntity, When mapping to AppUiModel, Then return valid AppUiModel`() {
        val appEntity = AppEntity("foo", "bar", "baz")
        val appUiModel = testSubject.mapToUiModel(appEntity)

        assertEquals(appEntity.name, appUiModel.name)
        assertEquals(appEntity.packageName, appUiModel.packageName)
        assertEquals(appEntity.versionName, appUiModel.versionName)

        // 3 fields + Parcelable.CREATOR
        // assertEquals(4, appUiModel.javaClass.declaredFields.size)
    }
}
