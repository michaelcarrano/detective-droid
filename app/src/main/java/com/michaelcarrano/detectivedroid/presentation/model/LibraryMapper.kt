package com.michaelcarrano.detectivedroid.presentation.model

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import javax.inject.Inject

open class LibraryMapper @Inject constructor() :
    Mapper<LibraryUiModel, LibraryEntity> {

    override fun mapToUiModel(type: LibraryEntity) =
        LibraryUiModel(type.name, type.source, type.classPath)
}
