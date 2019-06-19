package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class NewsLead(
    val id: Long,
    val title: String,
    val lead: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String,
    val type: String
)
