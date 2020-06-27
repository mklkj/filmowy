package io.github.mklkj.filmowy.api.pojo

import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class News(
    val newsId: Long, // used only for navigate
    val title: String,
    val lead: String?,
    val content: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String?,
    val source: String,
    val author: String
) : Serializable {

    companion object {
        fun get(id: Long, name: String = "", poster: String? = null, publicationTime: LocalDateTime = now()) = News(
            newsId = id,
            title = name,
            newsImageUrl = poster,
            lead = null,
            content = "",
            publicationTime = publicationTime,
            source = "",
            author = ""
        )
    }
}
