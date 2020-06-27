package io.github.mklkj.filmowy.api.pojo

import java.io.Serializable
import java.time.LocalDate

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
) : Serializable {
    companion object {
        fun get(id: Long, name: String = "", poster: String? = null) = Person(
            personId = id,
            name = name,
            realName = null,
            birthDate = null,
            birthPlace = null,
            deathDate = null,
            votesCount = 0,
            avgRate = .0,
            imagePath = poster,
            hasBiography = false,
            filmKnownFor = null,
            height = null,
            sex = 0
        )
    }
}
