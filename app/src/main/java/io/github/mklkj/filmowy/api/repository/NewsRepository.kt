package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapNews
import io.github.mklkj.filmowy.api.mapper.mapNewsComments
import io.github.mklkj.filmowy.api.mapper.mapNewsList
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.NewsComment
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.reactivex.Single
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: ApiService,
    private val scrapper: ScrapperService,
    private val preferences: SharedPreferences
) {

    fun getNewsList(page: Int): Single<List<NewsLead>> {
        return api.getWithMethod("getNewsList".asMethod(page)).map { it.mapNewsList() }
    }

    fun getArticle(id: Long, title: String = ""): Single<News> {
        return api.getWithMethod("getNews".asMethod((id))).map { it.mapNews(id) }.flatMap { news ->
            // TODO: Add switch in settings
            if (preferences.getBoolean("USE_SCRAPPER", true)) scrapper.getArticle(title, id).map {
                news.copy().apply {
                    contentHtml = it.content.apply { select("script, svg").remove() }.html()
                }
            }
            else Single.just(news)
        }
    }

    fun getNewsComments(newsId: Long, page: Int): Single<List<NewsComment>> {
        return api.getWithMethod("getNewsComments".asMethod(newsId, page.toLong())).map { it.mapNewsComments(newsId) }
    }
}
