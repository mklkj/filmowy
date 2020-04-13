package io.github.mklkj.filmowy.api.ajax

import com.google.gson.annotations.SerializedName

data class FilmVote(

    @SerializedName("t")
    val timestamp: Long,

    @SerializedName("eId")
    val eId: Int,

    @SerializedName("uId")
    val uId: Int,

    @SerializedName("r")
    val r: Int,

    @SerializedName("d")
    val date: VoteDate,

    @SerializedName("a")
    val a: Int
) {

    data class VoteDate(
        @SerializedName("y")
        val year: Int,

        @SerializedName("m")
        val month: Int,

        @SerializedName("d")
        val day: Int
    )
}
