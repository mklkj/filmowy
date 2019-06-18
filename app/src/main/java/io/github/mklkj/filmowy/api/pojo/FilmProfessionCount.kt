package io.github.mklkj.filmowy.api.pojo

data class FilmProfessionCount(
    val filmId: Int,
    val assocType: FilmPerson.AssocType,
    val assocCount: Int
)
