package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDateTime

data class FilmNearestBroadcast(
    val filmId: Long,
    val tvChannelId: Long,
    val dateTime: LocalDateTime,
    val id: Long,
    val description: String
)
