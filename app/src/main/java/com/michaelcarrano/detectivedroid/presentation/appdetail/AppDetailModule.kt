package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.data.LibraryRepository
import com.michaelcarrano.detectivedroid.data.LibraryRepositoryImpl
import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppDetailModule {

    @Binds
    abstract fun libraryRepository(libraryRepositoryImpl: LibraryRepositoryImpl): LibraryRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideAppListViewModelFactory(scanAppUseCase: ScanAppUseCase) =
            AppDetailViewModelFactory(null, scanAppUseCase)
    }
}
