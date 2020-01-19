package com.michaelcarrano.detectivedroid.presentation.appdetail

import com.michaelcarrano.detectivedroid.domain.ScanAppUseCase
import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AppDetailViewModel(
    initialState: State?,
    private val scanAppUseCase: ScanAppUseCase
) : BaseViewModel<Action, State>() {

    override val initialState = initialState ?: State(isIdle = true)

    private val reducer: Reducer<State, Change> = { state, change ->
        when (change) {
            is Change.Scanning -> state.copy(
                isIdle = false,
                isScanning = true,
                libraries = emptyList(),
                isError = false
            )
            is Change.NoLibrariesFound -> state.copy(
                isScanning = false,
                libraries = emptyList(),
                isError = false
            )
            is Change.Libraries -> state.copy(
                isScanning = false,
                libraries = change.libraries
            )
            is Change.Error -> state.copy(
                isScanning = false,
                isError = true
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val scanAppChange = actions.ofType<Action.ScanApp>()
            .switchMap { it ->
                scanAppUseCase.scanApp(it.packageName)
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<Change> { Change.Libraries(it) }
                    .defaultIfEmpty(Change.NoLibrariesFound)
                    .onErrorReturn { Change.Error(it) }
                    .startWith(Change.Scanning)
            }

        disposables += scanAppChange
            .scan(initialState, reducer)
            .filter { !it.isIdle }
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue, Timber::e)
    }
}
