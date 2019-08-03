package io.github.mklkj.filmowy.api.mapper

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.toLocalDate

fun JsonArray.mapUserData(): UserData {
    return UserData(
        userId = get(3).asLong,
        nick = get(0).asString,
        imagePath = get(1).asString,
        name = get(2).asString,
        gender = when(get(4).asString.toUpperCase()) {
            "M" -> 1
            "F" -> 2
            else -> 0
        },
        birthday = get(5).asString.run {
            when (this.length) {
                4 -> toLocalDate("yyyy")
                7 -> toLocalDate("yyyy-MM")
                10 -> toLocalDate("yyyy-MM-dd")
                else -> null
            }
        }
    )
}
