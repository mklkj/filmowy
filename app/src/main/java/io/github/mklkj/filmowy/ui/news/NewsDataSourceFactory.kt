package io.github.mklkj.filmowy.ui.news

import androidx.paging.DataSource
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.reactivex.disposables.CompositeDisposable

class NewsDataSourceFactory(
    private val repository: NewsRepository,
    private val disposable: CompositeDisposable
) : DataSource.Factory<Int, NewsLead>() {

    override fun create(): DataSource<Int, NewsLead> {
        return NewsDataSource(repository, disposable)
    }
}
