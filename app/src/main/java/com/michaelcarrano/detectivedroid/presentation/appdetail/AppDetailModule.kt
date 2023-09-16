package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.data.LibraryRepository
import com.michaelcarrano.detectivedroid.data.LibraryRepositoryImpl
import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class AppDetailModule {

    @Binds
    abstract fun libraryRepository(libraryRepositoryImpl: LibraryRepositoryImpl): LibraryRepository

    companion object {
        @Provides
        fun provideAppListViewModelFactory(scanAppUseCase: ScanAppUseCase) =
            AppDetailViewModelFactory(null, scanAppUseCase)
    }
}
