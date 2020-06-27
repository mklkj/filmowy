package io.github.mklkj.filmowy.ui.my

import androidx.hilt.lifecycle.ViewModelInject
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.repository.MyRepository
import io.github.mklkj.filmowy.base.BaseDataSource
import io.github.mklkj.filmowy.base.BasePagedViewModel
import io.reactivex.disposables.CompositeDisposable

class MyViewModel @ViewModelInject constructor(private val myRepository: MyRepository) :
    BasePagedViewModel<FriendVoteFilmEvent, MyViewModel.MyDataSource>() {

    override val sourceFactory by lazy { BaseDataSource.Factory { MyDataSource(myRepository, disposable) } }

    val votes = getPagedList()

    class MyDataSource(private val repo: MyRepository, disposable: CompositeDisposable) : BaseDataSource<FriendVoteFilmEvent>(disposable) {

        override fun getListByPageNumber(page: Int) = repo.getFriendVoteFilmEvents(page)

        override fun getFirstPageNumber() = 1
    }
}
