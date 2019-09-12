package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class FilmForumThread(
    val topic: String,
    val url: String,
    val authorId: Int,
    val author: String,
    val authorProfileUrl: String,
    val authorAvatarUrl: String,
    val date: LocalDateTime,
    val rating: Int,
    val content: String,
    val thumbsUp: Int,
    val topicAnswers: Int,
    val lastReplayUrl: String,
    val lastReplayUser: String,
    val lastReplayUserId: Int,
    val lastReplayDate: LocalDateTime?
)
