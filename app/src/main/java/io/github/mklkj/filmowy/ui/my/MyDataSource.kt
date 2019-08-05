package io.github.mklkj.filmowy.ui.my

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.repository.MyRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MyDataSource(private val repo: MyRepository, private val disposable: CompositeDisposable) : PageKeyedDataSource<Int, FriendVoteFilmEvent>() {

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    private var retryCompletable: Completable? = null

    class Factory(private val repo: MyRepository, private val disposable: CompositeDisposable) : DataSource.Factory<Int, FriendVoteFilmEvent>() {

        val dataSourceLiveData = MutableLiveData<MyDataSource>()

        override fun create(): DataSource<Int, FriendVoteFilmEvent> {
            val myDataSource = MyDataSource(repo, disposable)
            dataSourceLiveData.postValue(myDataSource)
            return myDataSource
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
                    .subscribe({}, { throwable -> Timber.e(throwable) })
            )
        }
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FriendVoteFilmEvent>) {
        disposable.add(repo.getFriendVoteFilmEvents(1)
            .doOnSubscribe {
                initialLoad.postValue(NetworkState.LOADING)
                networkState.postValue(NetworkState.LOADING)
            }
            .subscribe({ news ->
                setRetry(null)
                NetworkState.LOADED.let {
                    initialLoad.postValue(it)
                    networkState.postValue(it)
                }
                callback.onResult(news, 0, 2)
            }) { throwable ->
                Timber.e(throwable)

                setRetry(Action { loadInitial(params, callback) })
                NetworkState.error(throwable.message).let {
                    initialLoad.postValue(it)
                    networkState.postValue(it)
                }
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FriendVoteFilmEvent>) {
        disposable.add(repo.getFriendVoteFilmEvents(params.key)
            .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
            .subscribe({ news ->
                setRetry(null)
                networkState.postValue(NetworkState.LOADED)
                callback.onResult(news, params.key + 1)
            }) { throwable ->
                Timber.e(throwable)

                setRetry(Action { loadAfter(params, callback) })
                networkState.postValue(NetworkState.error(throwable.message))
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FriendVoteFilmEvent>) {
        // loading only to forward direction
    }
}
