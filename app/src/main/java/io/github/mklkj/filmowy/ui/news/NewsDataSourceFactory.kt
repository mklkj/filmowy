package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
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
