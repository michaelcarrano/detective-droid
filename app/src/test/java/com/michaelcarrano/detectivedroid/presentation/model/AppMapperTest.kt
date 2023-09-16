package com.michaelcarrano.detectivedroid.presentation.model

import android.content.res.Resources
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class AppMapperTest {

    private val resources = mock<Resources>(defaultAnswer = Mockito.RETURNS_DEEP_STUBS) {
        on { getString(android.R.string.unknownName) } doReturn "Unknown"
    }

    private val testSubject = AppMapper(resources)

    @Test
    fun `Given AppEntity, When mapping to AppUiModel, Then return valid AppUiModel`() {
        val appEntity = AppEntity("foo", "bar", "baz", true)
        val appUiModel = testSubject.mapToUiModel(appEntity)

        assertEquals(appEntity.name, appUiModel.name)
        assertEquals(appEntity.packageName, appUiModel.packageName)
        assertEquals(appEntity.versionName, appUiModel.versionName)

        // 3 fields + Parcelable.CREATOR
        // assertEquals(4, appUiModel.javaClass.declaredFields.size)
    }

    @Test
    fun `Given AppEntitys, When mapping to AppUiModels, Then return valid AppUiModels`() {
        val appEntitys = listOf(
            AppEntity("foo1", "bar1", "baz1", true),
            AppEntity("foo2", "bar2", "baz2", true),
        )
        val appUiModels = testSubject.mapToUiModels(appEntitys)

        assertEquals(appEntitys.size, appUiModels.size)

        for (i in 0 until appEntitys.count()) {
            assertEquals(appEntitys[i].name, appUiModels[i].name)
            assertEquals(appEntitys[i].packageName, appUiModels[i].packageName)
            assertEquals(appEntitys[i].versionName, appUiModels[i].versionName)
        }
    }

    @Test
    fun `Given AppEntitys with null values, When mapping to AppUiModels, then return valid AppUiModels with Unknown set as value`() {
        val appEntity = AppEntity(null, null, null, true)
        val appUiModel = testSubject.mapToUiModel(appEntity)

        val UNKNOWN = "Unknown"
        assertEquals(UNKNOWN, appUiModel.name)
        assertEquals(UNKNOWN, appUiModel.packageName)
        assertEquals(UNKNOWN, appUiModel.versionName)
    }
}
