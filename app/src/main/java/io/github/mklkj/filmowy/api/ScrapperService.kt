package io.github.mklkj.filmowy.api

import io.github.mklkj.filmowy.api.scrapper.response.ArticleResponse
import io.github.mklkj.filmowy.api.scrapper.response.FilmSeasonEpisodesResponse
import io.github.mklkj.filmowy.api.scrapper.response.ForumThreadsList
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ScrapperService {

    @GET("news/{slug}-{id}")
    fun getArticle(@Path("slug") slug: String, @Path("id") id: Long): Single<ArticleResponse>

    @GET("serial/{name}/episode/{season}/list")
    fun getSeasonEpisodes(@Path("name") name: String, @Path("season") season: Int): Single<FilmSeasonEpisodesResponse>

//    @Headers("X-Requested-With: XMLHttpRequest")
//    @GET("{type}/{name}/discussion")
    @GET
    fun getForumThreads(@Url url: String, @Query("page") page: Int = 1): Single<ForumThreadsList>
}
