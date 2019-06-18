package io.github.mklkj.filmowy.api.pojo

data class FilmImage(
    val filmId: Int,
    val imagePath: String,
    val persons: List<FilmPerson>?,
    val photoSources: List<String>?
)
