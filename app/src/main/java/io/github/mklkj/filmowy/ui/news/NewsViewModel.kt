package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.base.BaseDataSource
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val sourceFactory by lazy { BaseDataSource.Factory { NewsDataSource(newsRepository, disposable) } }

    class NewsDataSource(private val repo: NewsRepository, disposable: CompositeDisposable) : BaseDataSource<NewsLead>(disposable) {

        override fun getListByPageNumber(page: Int) = repo.getNewsList(page)
    }

    val news: LiveData<PagedList<NewsLead>>
        get() = LivePagedListBuilder(
            sourceFactory,
            PagedList.Config.Builder().setPageSize(20).build()
        ).build()

    val isRefresh = MutableLiveData(false)

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(sourceFactory.dataSourceLiveData) {
            it.initialLoad.apply {
                if (value?.status == NetworkState.LOADED.status) isRefresh.postValue(false)
            }
        }

    override val networkState
        get() = Transformations.switchMap(sourceFactory.dataSourceLiveData) { it.networkState } as MutableLiveData

    fun refresh() {
        sourceFactory.dataSourceLiveData.value?.invalidate()
    }

    fun retry() {
        sourceFactory.dataSourceLiveData.value?.retry()
    }
}
