package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.mapper.mapSearchResults
import io.github.mklkj.filmowy.utils.flowWithResource
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiService) {

    fun search(string: String) = flowWithResource {
        api.search(string).mapSearchResults()
    }
}
