package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import javax.inject.Inject

class LibraryMapper @Inject constructor() : Mapper<LibraryUiModel, LibraryEntity> {

    override fun mapToUiModel(type: LibraryEntity) =
        LibraryUiModel(type.name, type.source, type.classPath)

    override fun mapToUiModels(type: List<LibraryEntity>): List<LibraryUiModel> =
        type.map { mapToUiModel(it) }
}
