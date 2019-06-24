package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapBornTodayPersons
import io.github.mklkj.filmowy.api.mapper.mapPersonBiography
import io.github.mklkj.filmowy.api.mapper.mapPersonFilms
import io.github.mklkj.filmowy.api.pojo.PersonBiography
import io.github.mklkj.filmowy.api.pojo.PersonBirthdate
import io.github.mklkj.filmowy.api.pojo.PersonFilm
import io.reactivex.Single
import javax.inject.Inject


class PersonRepository @Inject constructor(private val api: ApiService) {

    fun getBornTodayPersons(): Single<List<PersonBirthdate>> {
        return api.getWithMethod("getBornTodayPersons".asMethod()).map { it.mapBornTodayPersons() }
    }

    fun getPersonBiography(personId: Long): Single<PersonBiography> {
        return api.getWithMethod("getPersonBiography".asMethod(personId)).map { it.mapPersonBiography() }
    }

    fun getPersonFilms(personId: Long, filmType: Int, type: Int, offset: Int, limit: Int): Single<List<PersonFilm>> {
        return api.getWithMethod("getPersonFilms".asMethod(personId, filmType, type, offset, limit)).map { it.mapPersonFilms(personId, filmType, type) }
    }
}
