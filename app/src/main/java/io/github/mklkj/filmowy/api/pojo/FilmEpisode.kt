package io.github.mklkj.filmowy.api.pojo

import java.time.LocalDate

data class FilmEpisode(
    val id: Int,
    val image: String,
    val season: Int,
    val number: Int,
    val premiereDate: LocalDate,
    val title: String,
    val avgRate: String,
    val rate: Int
)
