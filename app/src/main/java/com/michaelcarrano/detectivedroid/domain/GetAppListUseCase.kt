package com.michaelcarrano.detectivedroid.domain

import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.presentation.model.AppMapper
import com.michaelcarrano.detectivedroid.presentation.model.AppUiModel
import io.reactivex.Single
import javax.inject.Inject

class GetAppListUseCase @Inject constructor(
    private val repo: AppRepository,
    private val mapper: AppMapper
) {
    fun loadApps(showSystemApp: Boolean): Single<List<AppUiModel>> =
        repo.getApplications()
            .map { apps ->
                apps.filter { showSystemApp || it.userApp }
            }
            .map { apps ->
                apps.map { mapper.mapToUiModel(it) }
            }

    fun searchApps(query: String, showSystemApp: Boolean): Single<List<AppUiModel>> =
        loadApps(showSystemApp)
            .map { apps ->
                apps.filter { it.name.contains(query, true) }
            }
}
