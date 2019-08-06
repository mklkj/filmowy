package io.github.mklkj.filmowy.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.repository.MyRepository
import io.github.mklkj.filmowy.base.BaseDataSource
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MyViewModel @Inject constructor(private val myRepository: MyRepository) : BaseViewModel() {

    private val sourceFactory by lazy { BaseDataSource.Factory { MyDataSource(myRepository, disposable) } }

    class MyDataSource(private val repo: MyRepository, disposable: CompositeDisposable) : BaseDataSource<FriendVoteFilmEvent>(disposable) {

        override fun getFirstPageNumber() = 1

        override fun getListByPageNumber(page: Int) = repo.getFriendVoteFilmEvents(page)
    }

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
