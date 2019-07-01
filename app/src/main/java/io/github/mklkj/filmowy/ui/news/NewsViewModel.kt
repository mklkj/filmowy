package io.github.mklkj.filmowy.ui.news

import androidx.lifecycle.LiveData
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.repository.NewsRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.ui.BaseViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : BaseViewModel() {

    val news: LiveData<List<NewsLead>>
        get() {
            return newsRepository.getNewsList(1).subscribeOn(Schedulers.io()).toFlowable().toLiveData()
        }
}
