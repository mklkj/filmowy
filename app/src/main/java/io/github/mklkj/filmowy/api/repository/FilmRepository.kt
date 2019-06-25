package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.asVarargMethod
import io.github.mklkj.filmowy.api.mapper.*
import io.github.mklkj.filmowy.api.pojo.*
import io.reactivex.Single
import javax.inject.Inject

class FilmRepository @Inject constructor(private val api: ApiService) {

    fun getFilmDescription(filmId: Int): Single<FilmDescription> {
        return api.getWithMethod("getFilmDescription".asMethod(filmId)).map { it.mapFilmDescription() }
    }

//    fun getFilmComments(filmId: Int, page: Int): Single<JsonArray> {
//        return api.getWithMethod("getFilmComments".asMethod(filmId, page * 5, (page + 1) * 5))
//    }

    fun getFilmImages(filmId: Int, page: Int): Single<List<FilmImage>> {
        return api.getWithMethod("getFilmImages".asMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmImages(filmId) }
    }

    fun getFilmInfoFull(filmId: Int): Single<Film> {
        return api.getWithMethod("getFilmInfoFull".asMethod(filmId)).map { it.mapFilmFullInfo() }
    }

    fun getFilmPersons(filmId: Int, type: FilmPerson.AssocType, page: Int): Single<List<FilmPerson>> {
        return api.getWithMethod("getFilmPersons".asMethod(filmId, type.id, page * 50, 50)).map { it.mapFilmPersons(filmId, type) }
    }

    fun getFilmPersonsLead(filmId: Int, limit: Int): Single<List<FilmPersonsLead>> {
        return api.getWithMethod("getFilmPersonsLead".asMethod(filmId, limit)).map { it.mapFilmPersonsLead(filmId) }
    }

    fun getFilmProfessionCounts(filmId: Int): Single<List<FilmProfessionCount>> {
        return api.getWithMethod("getFilmProfessionCounts".asMethod(filmId)).map { it.mapFilmProfessionCount(filmId) }
    }

    fun getFilmReview(filmId: Int): Single<FilmReview> {
        return api.getWithMethod("getFilmReview".asMethod(filmId)).map { it.mapFilmReview() }
    }

    fun getFilmVideos(filmId: Int, page: Int): Single<List<FilmVideo>> {
        return api.getWithMethod("getFilmVideos".asMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmVideos(filmId) }
    }

    fun getFilmsInfoShort(vararg filmIds: Long): Single<List<Film>> {
        return api.getWithMethod("getFilmsInfoShort".asVarargMethod(*filmIds.toTypedArray())).map { it.mapFilmsInfoShort() }
    }

    fun getFilmsNearestBroadcasts(filmId: Int, page: Int): Single<List<FilmNearestBroadcast>> {
        return api.getWithMethod("getFilmsNearestBroadcasts".asVarargMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmsNearestBroadcasts() }
    }
}
