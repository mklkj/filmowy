package io.github.mklkj.filmowy.api.pojo

import org.threeten.bp.LocalDate

data class UserData(
    val userId: Long,
    val nick: String,
    val imagePath: String,
    val name: String,
    val gender: Int,
    val birthday: LocalDate?
) {
    companion object {
        const val KEY = "user_data_key"
    }
}
