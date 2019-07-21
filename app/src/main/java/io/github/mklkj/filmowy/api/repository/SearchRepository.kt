package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.mapper.mapSearchResults
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.reactivex.Single
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiService) {

    fun search(string: String): Single<List<SearchResult>> {
        return api.search(string).map { it.mapSearchResults() }
    }
}
