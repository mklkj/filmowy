package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.ajax.VotesResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun getWithMethod(@Query("methods") methods: String): JsonArray

    @POST("api")
    suspend fun postWithMethod(@Query("methods") methods: String): JsonArray

    @GET("search/live")
    suspend fun search(@Query("q") search: String): JsonArray

    @GET("serials/votes/{id}/{season}")
    suspend fun getUserVotes(@Path("id") filmId: Long, @Path("season") season: Int): VotesResponse

    @GET("json/user/{user_id}/filmVotesDetails")
    suspend fun getFilmVote(@Path("user_id") userId: Long, @Query("films") id: Long): List<FilmVote>
}
