package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class News(
    val title: String,
    val lead: String?,
    val content: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String?,
    val commentsCount: Int,
    val source: List<String>?,
    val author: List<String>?
)
