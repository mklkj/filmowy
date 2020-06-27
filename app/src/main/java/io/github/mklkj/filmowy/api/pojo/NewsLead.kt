package io.github.mklkj.filmowy.api.pojo

import java.io.Serializable
import java.time.LocalDateTime

data class NewsLead(
    val id: Long,
    val title: String,
    val lead: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String,
    val type: String
) : Serializable
