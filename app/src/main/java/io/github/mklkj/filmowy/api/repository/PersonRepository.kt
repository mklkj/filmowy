package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.*
import io.github.mklkj.filmowy.api.pojo.*
import io.github.mklkj.filmowy.utils.flowWithResource
import javax.inject.Inject


class PersonRepository @Inject constructor(private val api: ApiService) {

    suspend fun getBornTodayPersons(): List<PersonBirthdate> {
        return api.getWithMethod("getBornTodayPersons".asMethod()).mapBornTodayPersons()
    }

    suspend fun getPersonBiography(personId: Long): PersonBiography {
        return api.getWithMethod("getPersonBiography".asMethod(personId)).mapPersonBiography()
    }

    suspend fun getPersonFilms(personId: Long, filmType: Int, type: Int, offset: Int, limit: Int): List<PersonFilm> {
        return api.getWithMethod("getPersonFilms".asMethod(personId, filmType, type, offset, limit)).mapPersonFilms(personId, filmType, type)
    }

    suspend fun getPersonFilmsLead(personId: Long, limit: Int): List<PersonFilmsLead> {
        return api.getWithMethod("getPersonFilmsLead".asMethod(personId, limit)).mapPersonFilmsLead(personId)
    }

    suspend fun getPersonImages(personId: Long, page: Int): List<PersonImage> {
        return api.getWithMethod("getPersonImages".asMethod(personId, page, (page + 1))).mapPersonImages(personId)
    }

    fun getPersonInfoFull(personId: Long) = flowWithResource {
        api.getWithMethod("getPersonInfoFull".asMethod(personId)).mapPersonInfoFull(personId)
    }

    suspend fun getPersonProfessionCounts(personId: Long): List<PersonProfessionCount> {
        return api.getWithMethod("getPersonProfessionCounts".asMethod(personId)).mapPersonProfessionCounts(personId)
    }
}
