package io.github.mklkj.filmowy.ui.news

import androidx.hilt.lifecycle.ViewModelInject
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.base.BaseDataSource
import io.github.mklkj.filmowy.base.BasePagedViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.rx2.rxSingle

class NewsViewModel @ViewModelInject constructor(private val newsRepository: NewsRepository) :
    BasePagedViewModel<NewsLead, NewsViewModel.NewsDataSource>() {

    override val sourceFactory by lazy { BaseDataSource.Factory { NewsDataSource(newsRepository, disposable) } }

    val news = getPagedList()

    class NewsDataSource(private val repo: NewsRepository, disposable: CompositeDisposable) : BaseDataSource<NewsLead>(disposable) {

        override fun getListByPageNumber(page: Int) = rxSingle { repo.getNewsList(page) }
    }
}
