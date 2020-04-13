package io.github.mklkj.filmowy.ui.article

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

    fun loadArticle(news: News) {
        article.value = news
        disposable.add(newsRepository.getArticle(news.newsId, news.title)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.value = NetworkState.LOADING }
            .subscribe({
                article.postValue(it)
                networkState.value = NetworkState.LOADED
            }) {
                Timber.e(it)
                networkState.value = NetworkState.error(it.message)
            })
    }
}
