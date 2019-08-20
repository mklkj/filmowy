package io.github.mklkj.filmowy.api

import io.github.mklkj.filmowy.api.scrapper.response.ArticleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ScrapperService {

    @GET("news/{slug}-{id}")
    fun getArticle(@Path("slug") slug: String, @Path("id") id: Long): Single<ArticleResponse>
}