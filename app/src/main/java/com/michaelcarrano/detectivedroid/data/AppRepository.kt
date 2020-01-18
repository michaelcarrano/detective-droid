package com.michaelcarrano.detectivedroid.data

import com.michaelcarrano.detectivedroid.data.model.AppEntity
import io.reactivex.Single

interface AppRepository {
    fun getApplications(): Single<List<AppEntity>>
}
