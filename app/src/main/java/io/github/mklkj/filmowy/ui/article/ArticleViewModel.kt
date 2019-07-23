package io.github.mklkj.filmowy.ui.article

import androidx.lifecycle.LiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    fun getArticle(id: Long): LiveData<News> = newsRepository.getNews(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorReturnItem(News("", null, "", LocalDateTime.now(), null, 0, null, null))
        .doOnError {
            Timber.e(it)
            networkState.postValue(NetworkState.error(it.message))
        }
        .doOnSuccess {
            if (!it.lead.isNullOrBlank()) networkState.postValue(NetworkState.LOADED)
            else networkState.postValue(NetworkState.error("Wystąpił błąd podczas ładowania artykułu :("))
        }
        .toFlowable()
        .toLiveData()
}
