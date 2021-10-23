package com.michaelcarrano.detectivedroid.presentation.model

import android.content.res.Resources
import com.michaelcarrano.detectivedroid.data.model.AppEntity
import javax.inject.Inject

class AppMapper @Inject constructor(
    private val resources: Resources
) : Mapper<AppUiModel, AppEntity> {

    override fun mapToUiModel(type: AppEntity) =
        AppUiModel(value(type.name), value(type.packageName), value(type.versionName))

    override fun mapToUiModels(type: List<AppEntity>): List<AppUiModel> =
        type.map { mapToUiModel(it) }

    private fun value(s: String?) = s ?: resources.getString(android.R.string.unknownName)
}
