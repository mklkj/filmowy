package io.github.mklkj.filmowy.api

import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.scrapper.response.ArticleResponse
import io.github.mklkj.filmowy.api.scrapper.response.FilmSeasonEpisodesResponse
import io.github.mklkj.filmowy.api.scrapper.response.ForumThreadsList
import io.github.mklkj.filmowy.api.scrapper.response.SettingsResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ScrapperService {

    @POST("j_login")
    @FormUrlEncoded
    fun login(
        @Field("j_username") username: String,
        @Field("j_password") password: String,
        @Field("_login_redirect_url") redirectUrl: String = "%2Fsettings"
    ): Single<SettingsResponse>

    @GET("news/{slug}-{id}")
    fun getArticle(@Path("slug", encoded = true) slug: String, @Path("id") id: Long): Single<ArticleResponse>

    @GET
    fun getFilm(@Url url: String): Single<FilmFullInfo>

    @GET("{url}/episode/{season}/list")
    fun getSeasonEpisodes(@Path("url", encoded = true) name: String, @Path("season") season: Int): Single<FilmSeasonEpisodesResponse>

    @GET("{url}/episode/list")
    fun getEpisodes(@Path("url", encoded = true) name: String): Single<FilmSeasonEpisodesResponse>

    @FormUrlEncoded
    @POST("season/vote")
    fun voteForSeason(
        @Header("X-Artuser-Token") token: String,
        @Field("id") seriesId: Long,
        @Field("season") season: Int,
        @Field("rate") rate: Int
    ): Completable

    @FormUrlEncoded
    @POST("episode/vote")
    fun voteForEpisode(@Header("X-Artuser-Token") token: String, @Field("id") id: Int, @Field("rate") rate: Int): Completable

//    @Headers("X-Requested-With: XMLHttpRequest")
//    @GET("{type}/{name}/discussion")
    @GET
    fun getForumThreads(@Url url: String, @Query("page") page: Int = 1): Single<ForumThreadsList>
}
