package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.getNullable
import io.github.mklkj.filmowy.api.pojo.*
import io.github.mklkj.filmowy.api.toLocalDate

fun JsonArray.mapBornTodayPersons(): List<PersonBirthdate> {
    return map {
        val item = it.asJsonArray
        PersonBirthdate(
            id = item.get(0).asLong,
            name = item.get(1).asString,
            poster = item.get(2).asString,
            birthDate = item.get(3).asString.toLocalDate("yyy-MM-dd"),
            deathDate = item.getNullable(4)?.asString?.toLocalDate("yyyy-MM-dd")
        )
    }
}

fun JsonArray.mapPersonBiography(): PersonBiography {
    return PersonBiography(
        biography = get(0).asString
    )
}

fun JsonArray.mapPersonFilms(personId: Long, filmType: Int, assocType: Int): List<PersonFilm> {
    return map {
        val item = it.asJsonArray
        PersonFilm(
            personId = personId,
            filmType = filmType,
            assocType = assocType,
            filmId = item.get(0).asLong,
            assocName = item.getNullable(1)?.asString,
            assocAttributes = item.getNullable(5)?.asString,
            filmTitle = item.get(2).asString,
            filmImagePath = item.getNullable(3)?.asString,
            filmYear = item.getNullable(4)?.asInt,
            originalFilmTitle = item.getNullable(6)?.asString
        )
    }
}

fun JsonArray.mapPersonFilmsLead(personId: Long): List<PersonFilmsLead> {
    return map {
        val item = it.asJsonArray
        PersonFilmsLead(
            personId = personId,
            filmType = item.getNullable(0)?.asInt ?: 0,
            assocType = item.get(1).asInt,
            filmId = item.get(2).asLong,
            assocName = item.getNullable(3)?.asString,
            assocAttributes = item.getNullable(7)?.asString,
            filmTitle = item.getNullable(4)?.asString,
            filmImagePath = item.getNullable(5)?.asString,
            filmYear = item.getNullable(6)?.asInt
        )
    }
}

fun JsonArray.mapPersonImages(personId: Long): List<PersonImage> {
    return map {
        val item = it.asJsonArray
        PersonImage(
            personId = personId,
            imagePath = item.get(0).asString
        )
    }
}
