package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.AppEntity
import javax.inject.Inject

open class AppMapper @Inject constructor() : Mapper<AppUiModel, AppEntity> {

    override fun mapToUiModel(type: AppEntity) =
        AppUiModel(type.name, type.packageName, type.versionName)

    override fun mapToUiModels(type: List<AppEntity>): List<AppUiModel> =
        type.map { mapToUiModel(it) }
}
