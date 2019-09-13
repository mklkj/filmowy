package io.github.mklkj.filmowy.ui.forum

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.github.mklkj.filmowy.api.pojo.ForumThread
import io.github.mklkj.filmowy.api.repository.ForumRepository
import io.github.mklkj.filmowy.base.BaseDataSource
import io.github.mklkj.filmowy.base.BasePagedViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ForumViewModel @Inject constructor(private val forumRepository: ForumRepository) :
    BasePagedViewModel<ForumThread, ForumViewModel.ForumDataSource>() {

    val dataSource by lazy { ForumDataSource(forumRepository, disposable) }

    var pageSize = 30

    override val sourceFactory by lazy { BaseDataSource.Factory { dataSource } }

    val threads: LiveData<PagedList<ForumThread>> = getPagedList(pageSize)

    class ForumDataSource(private val forumRepository: ForumRepository, disposable: CompositeDisposable) :
        BaseDataSource<ForumThread>(disposable) {

        lateinit var url: String

        override fun getFirstPageNumber() = 1

        override fun getListByPageNumber(page: Int): Single<List<ForumThread>> = forumRepository.getForumThreadList(url, page)
    }
}
