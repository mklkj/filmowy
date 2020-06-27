package io.github.mklkj.filmowy.api.pojo

import java.time.LocalDateTime

data class FriendVoteFilmEvent(
    val filmId: Long,
    val friendUserId: Long,
    val userId: Long,
    val filmType: String,
    val filmTitle: String,
    val isNew: Boolean,
    val otherFilmsCount: Int,
    val datetime: LocalDateTime,
    val userFilmVote: UserFriendFilmVote
)
