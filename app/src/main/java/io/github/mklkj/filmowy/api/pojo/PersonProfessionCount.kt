package io.github.mklkj.filmowy.api.pojo

data class PersonProfessionCount(
    val personId: Long,
    val filmType: Int,
    val assocType: Int,
    val assocCount: Int
)
