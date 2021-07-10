package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapFriendVoteFilmEvents
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import javax.inject.Inject

class MyRepository @Inject constructor(private val api: ApiService) {

    suspend fun getFriendVoteFilmEvents(page: Int): List<FriendVoteFilmEvent> {
        return api.getWithMethod("getFriendVoteFilmEvents".asMethod(page)).mapFriendVoteFilmEvents()
    }
}
