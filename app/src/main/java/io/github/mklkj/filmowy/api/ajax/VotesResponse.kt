package io.github.mklkj.filmowy.api.ajax

import com.google.gson.annotations.SerializedName

data class VotesResponse(

    @SerializedName("watched")
    val isWatched: Boolean,

    @SerializedName("mostRecentVoteDate")
    val lastVoteTimestamp: Long,

    @SerializedName("mostRecentVoteDateForCurrentSeasonOrYear")
    val lastVoteTimestampForSeasonOrYear: Long,

    @SerializedName("votes")
    val votes: Map<String, Int>
) {
    class Vote(
        val isLoggedIn: Boolean,
        val vote: VotesResponse?
    )
}
