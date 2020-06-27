package io.github.mklkj.filmowy.api.pojo

import java.time.LocalDate

data class PersonBirthdate(
    val id: Long,
    val name: String,
    val poster: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate?
)
