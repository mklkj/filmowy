package io.github.mklkj.filmowy.api

import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.scrapper.response.ArticleResponse
import io.github.mklkj.filmowy.api.scrapper.response.FilmSeasonEpisodesResponse
import io.github.mklkj.filmowy.api.scrapper.response.ForumThreadsList
import io.github.mklkj.filmowy.api.scrapper.response.SettingsResponse
import retrofit2.http.*

interface ScrapperService {

    @POST("j_login")
    @FormUrlEncoded
    suspend fun login(
        @Field("j_username") username: String,
        @Field("j_password") password: String,
        @Field("_login_redirect_url") redirectUrl: String = "%2Fsettings"
    ): SettingsResponse

    @GET("news/{slug}-{id}")
    suspend fun getArticle(@Path("slug", encoded = true) slug: String, @Path("id") id: Long): ArticleResponse

    @GET
    suspend fun getFilm(@Url url: String): FilmFullInfo

    @GET("{url}/episode/{season}/list")
    suspend fun getSeasonEpisodes(@Path("url", encoded = true) name: String, @Path("season") season: Int): FilmSeasonEpisodesResponse

    @GET("{url}/episode/list")
    suspend fun getEpisodes(@Path("url", encoded = true) name: String): FilmSeasonEpisodesResponse

    @FormUrlEncoded
    @POST("season/vote")
    suspend fun voteForSeason(
        @Header("X-Artuser-Token") token: String,
        @Field("id") seriesId: Long,
        @Field("season") season: Int,
        @Field("rate") rate: Int
    )

    @FormUrlEncoded
    @POST("episode/vote")
    suspend fun voteForEpisode(@Header("X-Artuser-Token") token: String, @Field("id") id: Int, @Field("rate") rate: Int)

//    @Headers("X-Requested-With: XMLHttpRequest")
//    @GET("{type}/{name}/discussion")
    @GET
    suspend fun getForumThreads(@Url url: String, @Query("page") page: Int = 1): ForumThreadsList
}
