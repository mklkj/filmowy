package io.github.mklkj.filmowy.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    val networkState = MutableLiveData<NetworkState>()

    fun getArticle(id: Long): LiveData<News> = newsRepository.getNews(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        // TODO
        .doOnError {
            Timber.d(it)
            networkState.value = NetworkState.error(it.message)
        }
        .doOnSubscribe {
            networkState.value = NetworkState.LOADING
        }
        .doOnSuccess {
            networkState.value = NetworkState.LOADED
        }
        .toFlowable()
        .toLiveData()
}
