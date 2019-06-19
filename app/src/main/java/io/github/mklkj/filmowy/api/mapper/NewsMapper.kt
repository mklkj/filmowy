package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.pojo.NewsLead
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
