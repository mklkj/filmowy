package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.ajax.VotesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    fun getWithMethod(@Query("methods") methods: String): Single<JsonArray>

    @POST("api")
    fun postWithMethod(@Query("methods") methods: String): Single<JsonArray>

    @GET("search/live")
    fun search(@Query("q") search: String): Single<JsonArray>

    @GET("serials/votes/{id}/{season}")
    fun getUserVotes(@Path("id") filmId: Long, @Path("season") season: Int): Single<VotesResponse>

    @GET("json/user/{user_id}/filmVotesDetails")
    fun getFilmVote(@Path("user_id") userId: Long, @Query("films") id: Long): Single<List<FilmVote>>
}
