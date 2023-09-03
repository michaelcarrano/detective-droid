package com.michaelcarrano.detectivedroid.domain

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.michaelcarrano.detectivedroid.data.LibraryRepository
import com.michaelcarrano.detectivedroid.data.model.LibraryEntity
import com.michaelcarrano.detectivedroid.data.model.LibraryNotFoundEntity
import com.michaelcarrano.detectivedroid.presentation.model.LibraryMapper
import com.michaelcarrano.detectivedroid.presentation.model.LibraryUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observable.fromIterable
import io.reactivex.Single
import javax.inject.Inject

// TODO: Cache scan results
class ScanAppUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repo: LibraryRepository,
    private val mapper: LibraryMapper,
) {

    fun scanApp(packageName: String): Single<List<LibraryUiModel>> =
        repo.getLibraries()
            .flatMapObservable { fromIterable(it) }
            .map { library ->
                detectLibrary(packageName, library)
            }
            .filter { it != LibraryNotFoundEntity }
            .map { mapper.mapToUiModel(it) }
            .toList()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun detectLibrary(
        packageName: String,
        library: LibraryEntity,
    ): LibraryEntity {
        val packageContext = context.createPackageContext(
            packageName,
            Context.CONTEXT_INCLUDE_CODE or Context.CONTEXT_IGNORE_SECURITY,
        )

        var doesLibraryExist: Class<*>? = null
        try {
            doesLibraryExist = Class.forName(
                library.classPath,
                false,
                packageContext.classLoader,
            )
        } catch (e: Exception) {
            // Library not found in the app. No need to log exception to Timber.
        }

        return doesLibraryExist?.let { library } ?: run { LibraryNotFoundEntity }
    }
}
