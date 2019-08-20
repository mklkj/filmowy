package io.github.mklkj.filmowy.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ArticleViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    val article = MutableLiveData<News>()

    fun getArticle(news: News): LiveData<News> {
        disposable.add(newsRepository.getArticle(news.newsId, news.title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
            .subscribe({
                article.postValue(it)
                networkState.postValue(NetworkState.LOADED)
            }) {
                Timber.e(it)
                networkState.postValue(NetworkState.error(it.message))
            })
        return article
    }
}
