package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDate

data class FilmEpisode(
    val id: Int,
    val season: Int,
    val number: Int,
    val date: LocalDate,
    val title: String,
    val avgRate: Double?
)
