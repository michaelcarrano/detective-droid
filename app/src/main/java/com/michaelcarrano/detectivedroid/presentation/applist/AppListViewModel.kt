package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val SEARCH_DELAY_TIME = 500L

class AppListViewModel(
    initialState: State?,
    private val appListUseCase: GetAppListUseCase,
) : BaseViewModel<Action, State>() {

    override val initialState = initialState ?: State(isIdle = true)

    private val reducer: Reducer<State, Change> = { _, change ->
        when (change) {
            is Change.Loading -> State(isLoading = true)
            is Change.Apps -> State(change.apps)
            is Change.Error -> State(isError = true)
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val loadAppsChange = actions.ofType<Action.LoadApps>()
            .switchMap { it ->
                appListUseCase.loadApps(it.showSystemApp)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.Apps(it) }
                    .defaultIfEmpty(Change.Apps(emptyList()))
                    .onErrorReturn { Change.Error(it) }
                    .startWith(Change.Loading)
            }

        val searchAppsChange = actions.ofType<Action.Search>()
            .switchMap { it ->
                appListUseCase.searchApps(it.query!!, it.showSystemApp)
                    .delay(SEARCH_DELAY_TIME, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.Apps(it) }
                    .defaultIfEmpty(Change.Apps(emptyList()))
                    .onErrorReturn { Change.Error(it) }
            }

        val allChanges = Observable.merge(loadAppsChange, searchAppsChange)

        disposables += allChanges
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)
    }
}
