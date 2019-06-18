package io.github.mklkj.filmowy.api.pojo

data class FilmReview(
    val authorName: String,
    val authorUserId: Int?,
    val authorImagePath: String?,
    val content: String,
    val title: String
)
