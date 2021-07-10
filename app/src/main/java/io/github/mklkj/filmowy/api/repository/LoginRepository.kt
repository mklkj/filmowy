package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.mapper.mapUserData
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.utils.flowWithResource
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val scrapper: ScrapperService,
    private val preferences: SharedPreferences
) {

    fun login(login: String?, password: String?) = flowWithResource {
        scrapper.login(login.orEmpty(), password.orEmpty()).mapUserData()
    }

    fun saveUser(user: UserData) {
        preferences.edit().apply {
            putString(UserData.KEY, Gson().toJson(user))
            apply()
        }
    }

    fun getUser(): UserData = Gson().fromJson(preferences.getString(UserData.KEY, "{}"), UserData::class.java)
}
