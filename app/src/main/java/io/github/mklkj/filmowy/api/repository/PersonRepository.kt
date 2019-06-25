package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.*
import io.github.mklkj.filmowy.api.pojo.*
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

    fun getPersonFilmsLead(personId: Long, limit: Int): Single<List<PersonFilmsLead>> {
        return api.getWithMethod("getPersonFilmsLead".asMethod(personId, limit)).map { it.mapPersonFilmsLead(personId) }
    }

    fun getPersonImages(personId: Long, page: Int): Single<List<PersonImage>> {
        return api.getWithMethod("getPersonImages".asMethod(personId, page, (page + 1))).map { it.mapPersonImages(personId) }
    }

    fun getPersonInfoFull(personId: Long): Single<Person> {
        return api.getWithMethod("getPersonInfoFull".asMethod(personId)).map { it.mapPersonInfoFull(personId) }
    }

    fun getPersonProfessionCounts(personId: Long): Single<List<PersonProfessionCount>> {
        return api.getWithMethod("getPersonProfessionCounts".asMethod(personId)).map { it.mapPersonProfessionCounts(personId) }
    }
}
