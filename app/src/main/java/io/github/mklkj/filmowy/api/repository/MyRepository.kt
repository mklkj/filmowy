package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapFriendVoteFilmEvents
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.reactivex.Single
import javax.inject.Inject

class MyRepository @Inject constructor(private val api: ApiService) {

    fun getFriendVoteFilmEvents(page: Int): Single<List<FriendVoteFilmEvent>> {
        return api.getWithMethod("getFriendVoteFilmEvents".asMethod(page)).map { it.mapFriendVoteFilmEvents() }
    }
}
