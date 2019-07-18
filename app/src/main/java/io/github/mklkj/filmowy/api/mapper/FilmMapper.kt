package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.*
import io.github.mklkj.filmowy.api.toLocalDateTime

fun JsonArray.mapFilmDescription(): FilmDescription {
    return FilmDescription(
        description = getNullable(0)?.asString
    )
}

fun JsonArray.mapFilmImages(filmId: Int): List<FilmImage> {
    return map {
        val item = it.asJsonArray
        FilmImage(
            filmId = filmId,
            imagePath = item.get(0).asString,
            persons = item.getNullable(1)?.asJsonArray?.map { element ->
                val person = element.asJsonArray
                FilmPerson(
                    filmId = filmId,
                    assocType = FilmPerson.AssocType.getById(0),
                    personId = person.get(0).asLong,
                    personName = person.get(1).asString,
                    personImagePath = person.get(2).asString,
                    assocAttributes = null,
                    assocName = null
                )
            },
            photoSources = item.getNullable(2)?.asJsonArray?.map { element -> element.asString }
        )
    }
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
            originalTitle = getNullable(1)?.asString.orEmpty(),
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
            synopsis = getNullable(19)?.asString.orEmpty(),
            recommends = elementAtOrNull(23)?.asInt ?: 0 > 0,
            premiereWorldPublic = elementAtOrNull(28)?.asInt,
            premiereCountryPublic = elementAtOrNull(29)?.asInt
        )
    )
}

fun JsonArray.mapFilmPersons(filmId: Int, type: FilmPerson.AssocType): List<FilmPerson> {
    return map {
        val item = it.asJsonArray
        FilmPerson(
            filmId = filmId,
            assocType = type,
            personId = item.get(0).asLong,
            assocName = item.getNullable(1)?.asString,
            assocAttributes = item.getNullable(2)?.asString,
            personName = item.get(3).asString,
            personImagePath = item.getNullable(4)?.asString
        )
    }
}

fun JsonArray.mapFilmPersonsLead(filmId: Int): List<FilmPersonsLead> {
    return map {
        val item = it.asJsonArray
        FilmPersonsLead(
            filmId = filmId,
            assocType = FilmPerson.AssocType.getById(item.get(0).asInt),
            personId = item.get(1).asLong,
            assocName = item.getNullable(2)?.asString,
            assocAttributes = item.getNullable(3)?.asString,
            personName = item.getNullable(4)?.asString,
            personImagePath = item.getNullable(5)?.asString
        )
    }
}

fun JsonArray.mapFilmProfessionCount(filmId: Int): List<FilmProfessionCount> {
    return map {
        val item = it.asJsonArray
        FilmProfessionCount(
            filmId = filmId,
            assocType = FilmPerson.AssocType.getById(item.get(0).asInt),
            assocCount = item.get(1).asInt
        )
    }
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

fun JsonArray.mapFilmVideos(filmId: Int): List<FilmVideo> {
    return map {
        val item = it.asJsonArray
        FilmVideo(
            filmId = filmId,
            imagePath = item.get(1).asString,
            videoPath = item.get(2).asString
        )
    }
}

fun JsonArray.mapFilmsInfoShort(): List<Film> {
    return mapNotNull {
        if (it.isJsonNull) return@mapNotNull null
        val item = it.asJsonArray
        Film(
            title = item.get(0).asString,
            year = item.get(1).asInt,
            avgRate = item.getNullable(2)?.asDouble ?: .0,
            votesCount = item.getNullable(3)?.asInt ?: 0,
            duration = item.getNullable(4)?.asInt,
            imagePath = item.getNullable(5)?.asString,
            filmInfo = null
        )
    }
}

fun JsonArray.mapFilmsNearestBroadcasts(): List<FilmNearestBroadcast> {
    return map {
        val item = it.asJsonArray
        FilmNearestBroadcast(
            filmId = item.get(0).asLong,
            tvChannelId = item.get(1).asLong,
            dateTime = (item.get(3).asString + " " + item.get(2).asString).toLocalDateTime("yyyy-MM-dd HH:mm"),
            id = item.get(4).asLong,
            description = item.get(5).asString
        )
    }
}
