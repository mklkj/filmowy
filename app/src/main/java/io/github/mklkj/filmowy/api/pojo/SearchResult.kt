package io.github.mklkj.filmowy.api.pojo

import java.io.Serializable

interface SearchResult : Serializable {

    val type: Type

    val id: Int

    val title: String

    val poster: String

    enum class Type(val type: String) {
        FILM("f"),
        SERIES("s"),
        GAME("g"),
        PERSON("p"),
        CHANNEL("t"),
        CINEMA("c");

        companion object {
            private val values = values()
            fun getById(type: String) = values.firstOrNull { it.type == type } ?: FILM
            fun getByName(name: String) = values.firstOrNull { it.name == name } ?: FILM
        }
    }

    data class Film(
        override val type: Type,
        override val id: Int,
        override val title: String,
        override val poster: String,
        val originalTitle: String,
        val year: Int,
        val description: String
    ) : SearchResult

    data class Person(
        override val type: Type,
        override val id: Int,
        override val title: String,
        override val poster: String
    ) : SearchResult

    data class Channel(
        override val type: Type,
        override val id: Int,
        override val title: String,
        override val poster: String
    ) : SearchResult

    data class Cinema(
        override val type: Type,
        override val id: Int,
        override val title: String,
        override val poster: String,
        val city: String,
        val address: String,
        val coords: String
    ) : SearchResult
}
