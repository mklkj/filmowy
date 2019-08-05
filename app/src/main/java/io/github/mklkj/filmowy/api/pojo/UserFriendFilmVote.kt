package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class UserFriendFilmVote(
    val filmId: Long,
    val friendUserId: Long,
    val userId: Long,
    val rate: Int,
    val comment: String?,
    val commentsCount: Int,
    val likesCount: Int,
    val isLike: Boolean,
    val datetime: LocalDateTime
)
