package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.ui.BaseViewModel
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val sourceFactory by lazy { NewsDataSourceFactory(newsRepository, disposable) }

    val news: LiveData<PagedList<NewsLead>>
        get() = LivePagedListBuilder(
            sourceFactory, PagedList.Config.Builder()
                .setPageSize(20)
                .build()
        ).build()
}
