package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.*
import io.github.mklkj.filmowy.api.scrapper.response.FilmSeasonEpisodesResponse
import io.github.mklkj.filmowy.api.scrapper.response.ForumThreadsList
import io.github.mklkj.filmowy.api.toLocalDate
import io.github.mklkj.filmowy.api.toLocalDateTime
import timber.log.Timber

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

fun JsonArray.mapFilmFullInfo(id: Long): Film {
    val videos = getNullable(12)?.asJsonArray

    return Film(
        filmId = id,
        title = get(0).asString,
        avgRate = get(2).asDouble,
        votesCount = get(3).asInt,
        wantSee = getNullable(21)?.asInt ?: 0,
        year = get(5).asInt,
        duration = getNullable(6)?.asInt,
        imagePath = getNullable(11)?.asString,
        filmInfo = FilmInfo(
            originalTitle = getNullable(1)?.asString.orEmpty(),
            genres = get(4).asString,
            commentsCount = get(7).asString,
            forumUrl = getNullable(8)?.asString,
            hasReview = get(9).asInt > 0,
            hasDescription = get(10).asInt > 0,
            videoImageUrl = videos?.get(0)?.asString,
            videoUrl = videos?.get(1)?.asString,
            videoHDUrl = videos?.getNullable(2)?.asString,
            video480pUrl = videos?.getNullable(3)?.asString,
            ageRestriction = videos?.getNullable(4)?.asInt,
            premiereWorld = getNullable(13)?.asString,
            premiereCountry = getNullable(14)?.asString,
            filmType = get(15).asInt,
            seasonsCount = get(16).asInt,
            episodesCount = get(17).asInt,
            countriesString = getNullable(18)?.asString,
            synopsis = getNullable(19)?.asString,
            recommends = getNullable(23)?.asInt ?: 0 > 0,
            premiereWorldPublic = getNullable(28)?.asString?.let {
                when(it.length) {
                    10 -> it.toLocalDate("yyyy-MM-dd")
                    7 -> it.toLocalDate("yyyy-MM")
                    4 -> it.toLocalDate("yyyy")
                    else -> {
                        Timber.e("$it date can't be parsed")
                        null
                    }
                }
            },
            premiereCountryPublic = getNullable(29)?.asString?.toLocalDate("yyyy-MM-dd")
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
            filmId = -1,
            title = item.get(0).asString,
            year = item.get(1).asInt,
            avgRate = item.getNullable(2)?.asDouble ?: .0,
            votesCount = item.getNullable(3)?.asInt ?: 0,
            wantSee = 0, // TODO
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

fun FilmSeasonEpisodesResponse.mapFilmSeasonEpisodes(): List<FilmEpisode> {
    return episodes.map {
        FilmEpisode(
            id = it.id,
            image = it.image,
            season = it.season,
            number = it.number,
            premiereDate = it.premiereDate.toLocalDate("yyyy-MM-dd"),
            title = it.title,
            avgRate = it.averageRate.run {
                if (this == "brak głosów") null
                else removeSuffix(" średnia ocena").replace(",", ".").toDouble()
            }
        )
    }
}

fun ForumThreadsList.mapFilmForumThreadList(): List<ForumThread> {
    return threadList.map {
        ForumThread(
            topic = it.topic,
            author = it.authorName,
            authorId = it.authorId,
            authorProfileUrl = it.authorProfileUrl,
            authorAvatarUrl = it.authorAvatarUrl,
            content = it.content,
            date = it.date.toLocalDateTime("yyyy-MM-dd HH:mm:ss"),
            rating = it.rating.substringAfter(": ").ifBlank { null }?.toInt() ?: 0,
            url = it.topicUrl,
            thumbsUp = it.thumbsUp.let { count -> if (count.isEmpty()) "0" else count }.toInt(),
            topicAnswers = it.topicAnswers,
            lastReplayUser = it.lastReplayUser,
            lastReplayUserId = it.lastReplayUserId,
            lastReplayUrl = it.lastReplayUrl.trim(),
            lastReplayDate = it.lastReplayDate.ifBlank { null }?.toLocalDateTime("yyyy-MM-dd HH:mm:ss")
        )
    }
}
