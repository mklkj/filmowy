package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.exception.NotFoundException
import io.github.mklkj.filmowy.api.mapper.mapUserData
import io.github.mklkj.filmowy.api.pojo.UserData
import io.reactivex.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: ApiService,
    private val scrapper: ScrapperService,
    private val preferences: SharedPreferences
) {

    fun login(login: String?, password: String?): Single<UserData> {
        return scrapper.login(login.orEmpty(), password.orEmpty()).map { it.mapUserData() }
    }

    fun isUserLoggedIn(): Single<Boolean> {
        return api.getWithMethod("isLoggedUser".asMethod()).map { true }.onErrorResumeNext {
            if (it is NotFoundException) Single.just(false)
            else Single.error(it)
        }
    }

    fun saveUser(user: UserData) {
        preferences.edit().apply {
            putString(UserData.KEY, Gson().toJson(user))
            apply()
        }
    }

    fun getUser(): UserData = Gson().fromJson(preferences.getString(UserData.KEY, "{}"), UserData::class.java)
}
