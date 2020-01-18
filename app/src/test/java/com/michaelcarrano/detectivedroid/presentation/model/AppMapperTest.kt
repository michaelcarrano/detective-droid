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
            AppEntity("foo2", "bar2", "baz2", true)
        )
        val appUiModels = testSubject.mapToUiModels(appEntitys)

        for (i in 0 until appEntitys.count()) {
            assertEquals(appEntitys[i].name, appUiModels[i].name)
            assertEquals(appEntitys[i].packageName, appUiModels[i].packageName)
            assertEquals(appEntitys[i].versionName, appUiModels[i].versionName)
        }
    }
}
