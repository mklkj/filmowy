package io.github.mklkj.filmowy.api.pojo

import java.time.LocalDateTime

data class NewsComment(
    val newsId: Long,
    val userId: Long,
    val userPhoto: String?,
    val userName: String,
    val comment: String,
    val time: LocalDateTime
)
