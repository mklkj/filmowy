package io.github.mklkj.filmowy.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.repository.MyRepository
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import javax.inject.Inject

class MyViewModel @Inject constructor(private val myRepository: MyRepository) : BaseViewModel() {

    private val sourceFactory by lazy { MyDataSource.Factory(myRepository, disposable) }

    val votes: LiveData<PagedList<FriendVoteFilmEvent>>
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

