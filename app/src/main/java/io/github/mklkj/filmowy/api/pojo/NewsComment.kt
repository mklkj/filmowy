package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class NewsComment(
    val newsId: Long,
    val userId: Long,
    val userPhoto: String?,
    val userName: String,
    val comment: String,
    val time: LocalDateTime
)
