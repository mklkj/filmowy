package io.github.mklkj.filmowy.api.pojo

data class FilmInfo(
    val originalTitle: String,
    val genres: String,
    val commentsCount: String,
    val forumUrl: String,
    val hasReview: Boolean,
    val hasDescription: Boolean,
    val videoImageUrl: String,
    val videoUrl: String,
    val videoHDUrl: String?,
    val video480pUrl: String?,
    val ageRestriction: Int?,
    val premiereWorld: String,
    val premiereCountry: String?,
    val filmType: Int,
    val seasonsCount: Int,
    val episodesCount: Int,
    val countriesString: String,
    val synopsis: String,
    val recommends: Boolean,
    val premiereWorldPublic: Int?,
    val premiereCountryPublic: Int?
)
