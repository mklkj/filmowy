package io.github.mklkj.filmowy.api

import com.google.gson.JsonArray
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    fun getWithMethod(@Query("methods") methods: String): Single<JsonArray>

    @GET("search/live")
    fun search(@Query("q") search: String): Single<JsonArray>
}
