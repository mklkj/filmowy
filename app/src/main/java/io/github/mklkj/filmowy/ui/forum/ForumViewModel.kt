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

    override val sourceFactory by lazy { BaseDataSource.Factory { dataSource } }

    val threads: LiveData<PagedList<ForumThread>> = getPagedList(30)

    class ForumDataSource(private val forumRepository: ForumRepository, disposable: CompositeDisposable) :
        BaseDataSource<ForumThread>(disposable) {

        lateinit var type: String

        lateinit var name: String

        override fun getFirstPageNumber() = 1

        override fun getListByPageNumber(page: Int): Single<List<ForumThread>> = forumRepository.getForumThreadList(type, name, page)
    }
}
