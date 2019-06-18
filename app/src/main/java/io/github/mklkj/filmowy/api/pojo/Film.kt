package io.github.mklkj.filmowy.api.pojo

data class Film(
    val title: String,
    val avgRate: Double,
    val votesCount: Int,
    val year: Int?,
    val duration: Int?,
    val imagePath: String?,
    val filmInfo: FilmInfo?
)
