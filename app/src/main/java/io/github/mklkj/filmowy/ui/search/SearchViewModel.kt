package io.github.mklkj.filmowy.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.api.repository.SearchRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : BaseViewModel() {

    val networkState = MutableLiveData(NetworkState.LOADING)

    fun getSearchResults(query: String): LiveData<List<SearchResult>> = searchRepository.search(query)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorReturnItem(listOf(SearchResult.Channel(SearchResult.Type.CINEMA, -1, "", "")))
        .doOnError {
            Timber.e(it)
            networkState.postValue(NetworkState.error(it.message))
        }
        .doOnSuccess {
            if (it[0].id != -1) networkState.postValue(NetworkState.LOADED)
            else networkState.postValue(NetworkState.error("Wystąpił błąd podczas ładowania wyszukiwania :("))
        }
        .toFlowable()
        .toLiveData()

}
