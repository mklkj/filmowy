package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime
import java.io.Serializable

data class NewsLead(
    val id: Long,
    val title: String,
    val lead: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String,
    val type: String
) : Serializable
