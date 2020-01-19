package com.michaelcarrano.detectivedroid.presentation.applist

import com.michaelcarrano.detectivedroid.domain.GetAppListUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AppListViewModel(
    initialState: State?,
    private val appListUseCase: GetAppListUseCase
) : BaseViewModel<Action, State>() {

    override val initialState = initialState ?: State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Loading -> state.copy(
                isIdle = false,
                isLoading = true,
                apps = emptyList(),
                isError = false
            )
            is Change.Apps -> state.copy(
                isLoading = false,
                apps = change.apps
            )
            is Change.Error -> state.copy(
                isLoading = false,
                isError = true
            )
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

        // to handle multiple Changes, use Observable.merge to merge them into a single stream:
        // val allChanges = Observable.merge(loadNotesChange, ...)

        disposables += loadAppsChange
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)
    }
}
