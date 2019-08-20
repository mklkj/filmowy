package io.github.mklkj.filmowy.base

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.NetworkState.Companion.LOADED
import io.github.mklkj.filmowy.api.NetworkState.Companion.LOADING
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseDataSource<T>(private val disposable: CompositeDisposable) : PageKeyedDataSource<Int, T>() {

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    class Factory<T, D : BaseDataSource<T>>(private val factory: () -> D) : DataSource.Factory<Int, T>() {

        val dataSourceLiveData = MutableLiveData<BaseDataSource<T>>()

        override fun create() = factory().apply {
            dataSourceLiveData.postValue(this)
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) retryCompletable = null
        else retryCompletable = Completable.fromAction(action)
    }

    fun retry() {
        retryCompletable?.let { completable ->
            disposable.clear()
            disposable.add(
                completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable) })
            )
        }
    }

    abstract fun getListByPageNumber(page: Int): Single<List<T>>

    open fun getFirstPageNumber() = 0

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        disposable.add(getListByPageNumber(getFirstPageNumber())
            .doOnSubscribe {
                initialLoad.postValue(LOADING)
                networkState.postValue(LOADING)
            }
            .subscribe({ news ->
                setRetry(null)
                LOADED.let {
                    initialLoad.postValue(it)
                    networkState.postValue(it)
                }
                callback.onResult(news, 0, getFirstPageNumber() + 1)
            }) { throwable ->
                Timber.e(throwable)

                setRetry(Action { loadInitial(params, callback) })
                NetworkState.error(throwable.message).let {
                    initialLoad.postValue(it)
                    networkState.postValue(it)
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        disposable.add(getListByPageNumber(params.key)
            .doOnSubscribe { networkState.postValue(LOADING) }
            .subscribe({ news ->
                setRetry(null)
                networkState.postValue(LOADED)
                callback.onResult(news, params.key + 1)
            }) { throwable ->
                Timber.e(throwable)

                setRetry(Action { loadAfter(params, callback) })
                networkState.postValue(NetworkState.error(throwable.message))
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        // loading only to forward direction
    }
}
