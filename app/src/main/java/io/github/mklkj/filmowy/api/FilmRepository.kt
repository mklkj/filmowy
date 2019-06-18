package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.mapper.mapFilmDescription
import io.github.mklkj.filmowy.api.mapper.mapFilmFullInfo
import io.github.mklkj.filmowy.api.mapper.mapFilmReview
import io.github.mklkj.filmowy.api.mapper.mapFilmVideos
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.FilmDescription
import io.github.mklkj.filmowy.api.pojo.FilmReview
import io.github.mklkj.filmowy.api.pojo.FilmVideo
import io.reactivex.Single
import javax.inject.Inject

class FilmRepository @Inject constructor(private val api: ApiService) {

    fun getFilmDescription(filmId: Int): Single<FilmDescription> {
        return api.getWithMethod("getFilmDescription".asMethod(filmId)).map { it.mapFilmDescription() }
    }

//    fun getFilmComments(filmId: Int, page: Int): Single<JsonArray> {
//        return api.getWithMethod("getFilmComments".asMethod(filmId, page * 5, (page + 1) * 5))
//    }

    fun getFilmImages(filmId: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod("getFilmImages".asMethod(filmId, page * 5, (page + 1) * 5))
    }

    fun getFilmInfoFull(filmId: Int): Single<Film> {
        return api.getWithMethod("getFilmInfoFull".asMethod(filmId)).map { it.mapFilmFullInfo() }
    }

    fun getFilmPersons(filmId: Int, type: Int, page: Int): Single<JsonArray> {
        return api.getWithMethod("getFilmPersons".asMethod(filmId, type, page * 50, 50))
    }

    fun getFilmPersonsLead(filmId: Int, limit: Int): Single<JsonArray> {
        return api.getWithMethod("getFilmPersonsLead".asMethod(filmId, limit))
    }

    fun getFilmProfessionCounts(filmId: Int): Single<JsonArray> {
        return api.getWithMethod("getFilmProfessionCounts".asMethod(filmId))
    }

    fun getFilmReview(filmId: Int): Single<FilmReview> {
        return api.getWithMethod("getFilmReview".asMethod(filmId)).map { it.mapFilmReview() }
    }

    fun getFilmVideos(filmId: Int, page: Int): Single<List<FilmVideo>> {
        return api.getWithMethod("getFilmVideos".asMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmVideos(filmId) }
    }
}
