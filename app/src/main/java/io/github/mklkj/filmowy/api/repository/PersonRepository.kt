package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.mapper.mapBornTodayPersons
import io.github.mklkj.filmowy.api.mapper.mapPersonBiography
import io.github.mklkj.filmowy.api.pojo.PersonBiography
import io.github.mklkj.filmowy.api.pojo.PersonBirthdate
import io.reactivex.Single
import javax.inject.Inject


class PersonRepository @Inject constructor(private val api: ApiService) {

    fun getBornTodayPersons(): Single<List<PersonBirthdate>> {
        return api.getWithMethod("getBornTodayPersons".asMethod()).map { it.mapBornTodayPersons() }
    }

    fun getPersonBiography(personId: Long): Single<PersonBiography> {
        return api.getWithMethod("getPersonBiography".asMethod(personId)).map { it.mapPersonBiography() }
    }
}
