package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDate

data class Person(
    val personId: Long,
    val name: String,
    val realName: String?,
    val birthDate: LocalDate?,
    val birthPlace: String?,
    val deathDate: LocalDate?,
    val votesCount: Int,
    val avgRate: Double,
    val imagePath: String?,
    val hasBiography: Boolean,
    val filmKnownFor: Long?,
    val height: Int?,
    val sex: Int
)
