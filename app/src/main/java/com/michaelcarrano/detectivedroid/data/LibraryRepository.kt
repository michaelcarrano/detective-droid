package com.michaelcarrano.detectivedroid.data

import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import io.reactivex.Single

interface LibraryRepository {
    fun getLibraries(): Single<List<LibraryEntity>>
}
