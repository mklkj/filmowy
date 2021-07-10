package io.github.mklkj.filmowy.ui.article

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import kotlinx.coroutines.flow.onEach

class ArticleViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    val article = MutableLiveData<News>()

    fun loadArticle(news: News) {
        article.value = news
        newsRepository.getArticle(news.newsId, news.title)
            .handleGlobalStatus()
            .onEach { article.postValue(it.data?.copy(newsImageUrl = news.newsImageUrl)) }
            .launchOne()
    }
}
