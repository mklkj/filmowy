package io.github.mklkj.filmowy.api.pojo

data class PersonFilmsLead(
    val personId: Long,
    val filmType: Int,
    val assocType: Int,
    val filmId: Long,
    val assocName: String?,
    val assocAttributes: String?,
    val filmTitle: String?,
    val filmImagePath: String?,
    val filmYear: Int?
)
