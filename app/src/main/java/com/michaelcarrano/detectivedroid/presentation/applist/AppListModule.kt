package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.data.AppRepository
import com.michaelcarrano.detectivedroid.data.AppRepositoryImpl
import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppListModule {

    @Binds
    abstract fun appRespository(appRepositoryImpl: AppRepositoryImpl): AppRepository

    companion object {
        @Provides
        fun provideAppListViewModelFactory(getAppListUseCase: GetAppListUseCase) =
            AppListViewModelFactory(null, getAppListUseCase)
    }
}
