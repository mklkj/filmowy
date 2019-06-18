package io.github.mklkj.filmowy.api.pojo

data class FilmPersonsLead(
    val filmId: Int,
    val assocType: FilmPerson.AssocType,
    val personId: Long,
    val assocName: String?,
    val assocAttributes: String?,
    val personName: String?,
    val personImagePath: String?
)
