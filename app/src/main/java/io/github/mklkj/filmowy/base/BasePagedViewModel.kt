package io.github.mklkj.filmowy.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.NetworkState

abstract class BasePagedViewModel<T, D : BaseDataSource<T>> : BaseViewModel() {

    abstract val sourceFactory: BaseDataSource.Factory<T, D>

    fun getPagedList(pageSize: Int = 20): LiveData<PagedList<T>> {
        return LivePagedListBuilder(sourceFactory, PagedList.Config.Builder().setPageSize(pageSize).build()).build()
    }

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
