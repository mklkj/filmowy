package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.mapper.mapFilmForumThreadList
import io.github.mklkj.filmowy.api.pojo.ForumThread
import javax.inject.Inject

class ForumRepository @Inject constructor(private val scrapper: ScrapperService) {

    suspend fun getForumThreadList(url: String, page: Int = 1): List<ForumThread> {
        return scrapper.getForumThreads(url, page).mapFilmForumThreadList()
    }
}
