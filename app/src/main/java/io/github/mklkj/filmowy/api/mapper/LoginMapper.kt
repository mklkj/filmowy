package io.github.mklkj.filmowy.api.mapper

import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.scrapper.response.SettingsResponse
import java.net.URL

fun SettingsResponse.mapUserData(): UserData {
    return UserData(
        userId = id,
        nick = nick,
        name = name,
        birthday = null,
        gender = if (sex == "checked") 1 else 0,
        imagePath = URL(avatar).path.orEmpty().removePrefix("/u")
    )
}
