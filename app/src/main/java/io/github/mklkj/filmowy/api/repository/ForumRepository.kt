package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.mapper.mapFilmForumThreadList
import io.github.mklkj.filmowy.api.pojo.ForumThread
import io.reactivex.Single
import javax.inject.Inject

class ForumRepository @Inject constructor(private val scrapper: ScrapperService) {

    fun getForumThreadList(url: String, page: Int = 1): Single<List<ForumThread>> {
        return scrapper.getForumThreads(url, page).map { it.mapFilmForumThreadList() }
    }
}
