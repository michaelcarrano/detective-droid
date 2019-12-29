package com.michaelcarrano.detectivedroid.data

import com.michaelcarrano.detectivedroid.data.model.AppEntity
import io.reactivex.Single

interface AppRepository {
    fun getApplications(showSystemApp: Boolean = false): Single<List<AppEntity>>
}
