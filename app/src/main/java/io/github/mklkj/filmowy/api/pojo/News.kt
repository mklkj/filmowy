package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalDateTime.now
import java.io.Serializable

data class News(
    val newsId: Long, // used only for navigate
    val title: String,
    val lead: String?,
    val content: String,
    val publicationTime: LocalDateTime,
    val newsImageUrl: String?,
    val commentsCount: Int,
    val source: List<String>?,
    val author: List<String>?
) : Serializable {

    var contentHtml: String = ""

    companion object {
        fun get(id: Long, name: String = "", poster: String? = null, publicationTime: LocalDateTime = now()) = News(
            newsId = id,
            title = name,
            newsImageUrl = poster,
            lead = null,
            content = "",
            publicationTime = publicationTime,
            commentsCount = 0,
            source = null,
            author = null
        )
    }
}
