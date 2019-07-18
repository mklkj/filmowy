package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NewsDataSource(
    private val repository: NewsRepository,
    private val disposable: CompositeDisposable
) : PageKeyedDataSource<Int, NewsLead>() {

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    class Factory(
        private val repository: NewsRepository,
        private val disposable: CompositeDisposable
    ) : DataSource.Factory<Int, NewsLead>() {

        val newsDataSourceLiveData = MutableLiveData<NewsDataSource>()

        override fun create(): DataSource<Int, NewsLead> {
            val newsDataSource = NewsDataSource(repository, disposable)
            newsDataSourceLiveData.postValue(newsDataSource)
            return newsDataSource
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) retryCompletable = null
        else retryCompletable = Completable.fromAction(action)
    }

    fun retry() {
        retryCompletable?.let { completable ->
            disposable.add(
                completable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.e(throwable) })
            )
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, NewsLead>) {
        initialLoad.postValue(NetworkState.LOADING)
        networkState.postValue(NetworkState.LOADING)

        disposable.add(repository.getNewsList(0).subscribe({ news ->
            setRetry(null)
            NetworkState.LOADED.let {
                initialLoad.postValue(it)
                networkState.postValue(it)
            }
            callback.onResult(news, 0, 1)
        }) { throwable ->
            Timber.e(throwable)
            setRetry(Action { loadInitial(params, callback) })

            NetworkState.error(throwable.message).let {
                initialLoad.postValue(it)
                networkState.postValue(it)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, NewsLead>) {
        networkState.postValue(NetworkState.LOADING)

        disposable.add(repository.getNewsList(params.key).subscribe({ news ->
            setRetry(null)
            networkState.postValue(NetworkState.LOADED)
            callback.onResult(news, params.key + 1)
        }) { throwable ->
            Timber.e(throwable)
            setRetry(Action { loadAfter(params, callback) })
            networkState.postValue(NetworkState.error(throwable.message))
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, NewsLead>) {
        // loading only to forward direction
    }
}
