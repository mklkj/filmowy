package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.encodeFilmName
import io.github.mklkj.filmowy.api.mapper.mapNews
import io.github.mklkj.filmowy.api.mapper.mapNewsComments
import io.github.mklkj.filmowy.api.mapper.mapNewsList
import io.github.mklkj.filmowy.api.pojo.NewsComment
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.utils.flowWithResource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: ApiService,
    private val scrapper: ScrapperService
) {

    suspend fun getNewsList(page: Int): List<NewsLead> {
        return api.getWithMethod("getNewsList".asMethod(page)).mapNewsList()
    }

    fun getArticle(id: Long, title: String) = flowWithResource {
        scrapper.getArticle(title.encodeFilmName(), id).mapNews(id)
    }

    suspend fun getNewsComments(newsId: Long, page: Int): List<NewsComment> {
        return api.getWithMethod("getNewsComments".asMethod(newsId, page.toLong())).mapNewsComments(newsId)
    }
}
