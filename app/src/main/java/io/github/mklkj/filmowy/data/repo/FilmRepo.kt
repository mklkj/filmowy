package io.github.mklkj.filmowy.data.repo

import info.talacha.filmweb.api.FilmwebApi
import info.talacha.filmweb.search.models.FilmSearchResult
import io.reactivex.Single
import javax.inject.Inject

class FilmRepo @Inject constructor(private val filmwebApi: FilmwebApi) {

    fun findFilm(name: String): Single<List<FilmSearchResult>> {
        return Single.create {
            it.onSuccess(filmwebApi.findFilm(name))
        }
    }
}
