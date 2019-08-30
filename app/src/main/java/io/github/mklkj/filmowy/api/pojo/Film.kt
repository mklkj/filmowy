package io.github.mklkj.filmowy.api.pojo

import java.io.Serializable

data class Film(
    val filmId: Long, // used only to navigate
    var title: String,
    val avgRate: Double,
    val votesCount: Int,
    val wantSee: Int,
    var year: Int?,
    val duration: Int?,
    val imagePath: String?,
    val filmInfo: FilmInfo?
) : Serializable {
    companion object {
        fun get(id: Long, name: String = "", poster: String? = null) = Film(
            filmId = id,
            title = name,
            imagePath = poster,
            avgRate = .0,
            duration = null,
            filmInfo = null,
            votesCount = 0,
            wantSee = 0,
            year = null
        )
    }
}
