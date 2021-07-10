package io.github.mklkj.filmowy.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.SearchResult
import io.github.mklkj.filmowy.api.repository.SearchRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import kotlinx.coroutines.flow.onEach

class SearchViewModel @ViewModelInject constructor(private val searchRepository: SearchRepository) : BaseViewModel() {

    val searchResults = MutableLiveData<List<SearchResult>>()

    fun getSearchResults(query: String) {
        searchRepository.search(query)
            .handleGlobalStatus()
            .onEach {
                searchResults.value = it.data
            }
            .launchOne()
    }
}
