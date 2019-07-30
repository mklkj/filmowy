package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapNews
import io.github.mklkj.filmowy.api.mapper.mapNewsComments
import io.github.mklkj.filmowy.api.mapper.mapNewsList
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.NewsComment
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: ApiService) {

    fun getNewsList(page: Int): Single<List<NewsLead>> {
        return api.getWithMethod("getNewsList".asMethod(page)).map { it.mapNewsList() }
    }

    fun getNews(id: Long): Single<News> {
        return api.getWithMethod("getNews".asMethod((id))).map { it.mapNews(id) }
    }

    fun getNewsComments(newsId: Long, page: Int): Single<List<NewsComment>> {
        return api.getWithMethod("getNewsComments".asMethod(newsId, page.toLong())).map { it.mapNewsComments(newsId) }
    }
}
