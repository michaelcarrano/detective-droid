package com.michaelcarrano.detectivedroid.di

import com.michaelcarrano.detectivedroid.presentation.MainActivity
import com.michaelcarrano.detectivedroid.presentation.appdetail.AppDetailFragment
import com.michaelcarrano.detectivedroid.presentation.appdetail.AppDetailModule
import com.michaelcarrano.detectivedroid.presentation.applist.AppListFragment
import com.michaelcarrano.detectivedroid.presentation.applist.AppListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Scope

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AppListModule::class])
    abstract fun contributeAppListFragment(): AppListFragment

    @ContributesAndroidInjector(modules = [AppDetailModule::class])
    abstract fun contributeAppDetailFragment(): AppDetailFragment
}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope
