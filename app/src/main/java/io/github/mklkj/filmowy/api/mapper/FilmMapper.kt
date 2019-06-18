package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.FilmDescription
import io.github.mklkj.filmowy.api.pojo.FilmInfo
import io.github.mklkj.filmowy.api.pojo.FilmReview

fun JsonArray.mapFilmDescription(): FilmDescription {
    return FilmDescription(
        description = getNullable(0)?.asString
    )
}

fun JsonArray.mapFilmFullInfo(): Film {
    val videos = getNullable(12)?.asJsonArray

    return Film(
        title = get(0).asString,
        avgRate = get(2).asDouble,
        votesCount = get(3).asInt,
        year = get(5).asInt,
        duration = getNullable(6)?.asInt,
        imagePath = get(11).asString,
        filmInfo = FilmInfo(
            originalTitle = get(1).asString,
            genres = get(4).asString,
            commentsCount = get(7).asString,
            forumUrl = get(8).asString,
            hasReview = get(9).asInt > 0,
            hasDescription = get(10).asInt > 0,
            videoImageUrl = videos?.get(0)?.asString,
            videoUrl = videos?.get(1)?.asString,
            videoHDUrl = videos?.elementAtOrNull(2)?.asString,
            video480pUrl = videos?.elementAtOrNull(3)?.asString,
            ageRestriction = videos?.elementAtOrNull(4)?.asInt,
            premiereWorld = get(13).asString,
            premiereCountry = getNullable(14)?.asString,
            filmType = get(15).asInt,
            seasonsCount = get(16).asInt,
            episodesCount = get(17).asInt,
            countriesString = get(18).asString,
            synopsis = get(19).asString,
            recommends = elementAtOrNull(23)?.asInt ?: 0 > 0,
            premiereWorldPublic = elementAtOrNull(28)?.asInt,
            premiereCountryPublic = elementAtOrNull(29)?.asInt
        )
    )
}

fun JsonArray.mapFilmReview(): FilmReview {
    return FilmReview(
        authorName = get(0).asString,
        authorUserId = get(1)?.asInt,
        authorImagePath = get(2)?.asString,
        content = get(3).asString,
        title = get(4).asString
    )
}
