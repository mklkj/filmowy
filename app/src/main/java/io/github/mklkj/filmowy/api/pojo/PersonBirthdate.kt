package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDate

data class PersonBirthdate(
    val id: Long,
    val name: String,
    val poster: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate?
)
