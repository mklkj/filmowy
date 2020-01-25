package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.pojo.SearchResult

fun JsonArray.mapSearchResults(): List<SearchResult> {
    return asJsonArray.map {
        val item = it.asJsonArray
        when (SearchResult.Type.getById(item.get(0).asString)) {
            SearchResult.Type.FILM,
            SearchResult.Type.SERIES,
            SearchResult.Type.GAME -> item.mapFilmResult()
            SearchResult.Type.PERSON -> item.mapPersonResult()
            SearchResult.Type.CHANNEL -> item.mapChannelResult()
            SearchResult.Type.CINEMA -> item.mapCinemaResult()
        }
    }
}

private fun JsonArray.mapFilmResult() = SearchResult.Film(
    type = SearchResult.Type.getById(get(0).asString),
    id = get(1).asString.toInt(),
    poster = get(2).asString,
    title = get(3).asString,
    originalTitle = get(4).asString,
    year = get(6).asString.toInt(),
    description = get(7).asString
)

private fun JsonArray.mapPersonResult() = SearchResult.Person(
    type = SearchResult.Type.PERSON,
    id = get(1).asString.toInt(),
    title = get(3).asString,
    poster = get(2).asString,
    gender = when (get(4).asString) {
        "1" -> "Aktorka"
        "2" -> "Aktor"
        else -> "Aktorzyna"
    }
)

private fun JsonArray.mapChannelResult() = SearchResult.Channel(
    type = SearchResult.Type.CHANNEL,
    id = get(1).asString.toInt(),
    title = get(2).asString,
    poster = "/${get(1).asString.toInt()}.0.png"
)

private fun JsonArray.mapCinemaResult() = SearchResult.Cinema(
    type = SearchResult.Type.CINEMA,
    id = get(1).asString.toInt(),
    title = get(2).asString,
    poster = "", // TODO provide fallback
    city = get(3).asString,
    address = get(4).asString,
    coords = get(5).asString
)
