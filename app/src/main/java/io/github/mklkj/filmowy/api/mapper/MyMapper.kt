package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.pojo.UserFriendFilmVote
import io.github.mklkj.filmowy.api.toLocalDateTime

fun JsonArray.mapFriendVoteFilmEvents(): List<FriendVoteFilmEvent> {
    return asJsonArray.map {
        val item = it.asJsonArray
        FriendVoteFilmEvent(
            filmId = item.get(0).asLong,
            filmType = item.get(2).asString,
            filmTitle = item.get(3).asString,
            isNew = item.get(10).asInt == 1,
            userId = -1,
            friendUserId = item.get(1).asLong,
            otherFilmsCount = item.get(11).asInt,
            datetime = (item.get(13).asLong * 1000).toLocalDateTime(),
            userFilmVote = UserFriendFilmVote(
                filmId = item.get(0).asLong,
                friendUserId = item.get(1).asLong,
                userId = -1,
                rate = item.get(4).asInt,
                comment = item.getNullable(6)?.asString,
                commentsCount = item.get(7).asInt,
                likesCount = item.get(8).asInt,
                isLike = item.get(9).asInt == 1,
                datetime = (item.get(13).asLong * 1000).toLocalDateTime()
            )
        )
    }
}
