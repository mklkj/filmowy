package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.News
import io.github.mklkj.filmowy.api.pojo.NewsComment
import io.github.mklkj.filmowy.api.pojo.NewsLead
import io.github.mklkj.filmowy.api.scrapper.response.ArticleResponse
import io.github.mklkj.filmowy.api.toLocalDateTime

fun JsonArray.mapNewsList(): List<NewsLead> {
    return map {
        val item = it.asJsonArray
        NewsLead(
            id = item.get(0).asLong,
            title = item.get(1).asString,
            lead = item.get(2).asString,
            publicationTime = item.get(3).asLong.toLocalDateTime(),
            newsImageUrl = item.get(4).asString,
            type = item.get(5).asString
        )
    }
}

fun JsonArray.mapNews(id: Long): News {
    return News(
        newsId = id,
        title = get(0).asString,
        lead = getNullable(1)?.asString,
        content = get(2).asString,
        publicationTime = get(3).asLong.toLocalDateTime(),
        newsImageUrl = getNullable(4)?.asString,
        commentsCount = get(5).asInt,
        source = getNullable(6)?.asJsonArray?.map { it.asString },
        author = getNullable(7)?.asJsonArray?.map { it.asString }
    )
}

fun ArticleResponse.mapNews(id: Long): News {
    return News(
        newsId = id,
        content = content.apply { select("script, svg").remove() }.html().replace("\"/", "\"app://io.github.mklkj.filmowy/"),
        title = title,
        author = listOf(author),
        commentsCount = -1,
        lead = lead,
        newsImageUrl = newsImageUrl,
        publicationTime = date.html().split("\"")[1].toLocalDateTime("yyyy-MM-dd HH:mm:ss z"),
        source = listOf(source)
    )
}

fun JsonArray.mapNewsComments(newsId: Long): List<NewsComment> {
    return map {
        val item = it.asJsonArray
        NewsComment(
            newsId = newsId,
            userId = item.get(0).asLong,
            userPhoto = item.getNullable(1)?.asString,
            userName = item.get(2).asString,
            comment = item.get(3).asString,
            time = item.get(4).asLong.toLocalDateTime()
        )
    }
}
