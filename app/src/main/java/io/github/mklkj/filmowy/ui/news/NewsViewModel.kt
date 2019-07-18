package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val sourceFactory by lazy { NewsDataSource.Factory(newsRepository, disposable) }

    val news: LiveData<PagedList<NewsLead>>
        get() = LivePagedListBuilder(
            sourceFactory, PagedList.Config.Builder()
                .setPageSize(20)
                .build()
        ).build()

    val isRefresh = MutableLiveData(false)

    val refreshState: LiveData<NetworkState>
        get() = Transformations.switchMap(sourceFactory.newsDataSourceLiveData) {
            it.initialLoad.apply {
                if (value?.status == NetworkState.LOADED.status) isRefresh.postValue(false)
            }
        }

    val networkState: LiveData<NetworkState>
        get() = Transformations.switchMap(sourceFactory.newsDataSourceLiveData) { it.networkState }

    fun refresh() {
        sourceFactory.newsDataSourceLiveData.value?.invalidate()
    }

    fun retry() {
        sourceFactory.newsDataSourceLiveData.value?.retry()
    }
}
