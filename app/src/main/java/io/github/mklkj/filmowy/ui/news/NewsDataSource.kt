package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action

class NewsDataSource(private val repository: NewsRepository, private val disposable: CompositeDisposable) : PageKeyedDataSource<Int, NewsLead>() {

    // TODO
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    private fun setRetry(action: Action?) {
        if (action == null) retryCompletable = null
        else retryCompletable = Completable.fromAction(action)
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsLead>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        disposable.add(repository.getNewsList(0).subscribe({ news ->
            setRetry(null)
            NetworkState.LOADED.let {
                networkState.postValue(it)
                initialLoad.postValue(it)
            }
            callback.onResult(news, 0, 1)
        }) { throwable ->
            setRetry(Action { loadInitial(params, callback) })

            NetworkState.error(throwable.message).let {
                networkState.postValue(it)
                initialLoad.postValue(it)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsLead>) {
        networkState.postValue(NetworkState.LOADING)

        disposable.add(repository.getNewsList(params.key).subscribe({ news ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(news, params.key + 1)
        }) {
            setRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(it.message))
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsLead>) {
        // loading only to forward direction
    }
}
