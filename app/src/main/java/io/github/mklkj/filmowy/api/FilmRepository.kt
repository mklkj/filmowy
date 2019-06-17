package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray
import io.reactivex.Single
import javax.inject.Inject

class FilmRepository @Inject constructor(private val api: ApiService) {

    private fun formatMethod(method: String, vararg params: Int): String {
        return "$method ${params.joinToString(",", "[", "]")}\n"
    }

    fun getFilmDescription(filmId: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmDescription", filmId))
    }

    fun getFilmComments(filmId: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmComments", filmId, page * 5, (page + 1) * 5))
    }

    fun getFilmImages(filmId: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmImages", filmId, page * 5, (page + 1) * 5))
    }

    fun getFilmInfoFull(filmId: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmInfoFull", filmId))
    }

    fun getFilmPersons(filmId: Int, type: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmPersons", filmId, type, page * 50, 50))
    }

    fun getFilmPersonsLead(filmId: Int, limit: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmPersonsLead", filmId, limit))
    }

    fun getFilmProfessionCounts(filmId: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmProfessionCounts", filmId))
    }

    fun getFilmReview(filmId: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmReview", filmId))
    }

    fun getFilmVideos(filmId: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod(formatMethod("getFilmVideos", filmId, page * 100, (page + 1) * 100))
    }
}
