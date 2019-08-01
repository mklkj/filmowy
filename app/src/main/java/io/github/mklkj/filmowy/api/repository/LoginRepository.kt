package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.exception.NotFoundException
import io.github.mklkj.filmowy.api.mapper.mapUserData
import io.github.mklkj.filmowy.api.pojo.UserData
import io.reactivex.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: ApiService, private val preferences: SharedPreferences) {

    fun login(login: String?, password: String?): Single<UserData> {
        return api.postWithMethod("login".asMethod(login.quote(), password.quote(), 1)).map { it.mapUserData() }
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

    private fun String?.quote() = "\"$this\""
}
