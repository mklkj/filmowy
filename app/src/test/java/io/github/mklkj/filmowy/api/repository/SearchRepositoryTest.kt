package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import io.github.mklkj.filmowy.api.pojo.SearchResult
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

import org.junit.Assert.*
import retrofit2.create

class SearchRepositoryTest : BaseApiTest() {

    private val searchRepository by lazy { SearchRepository(getRetrofit().create()) }

    @Test
    fun search() {
        server.enqueue(MockResponse().setBody(getResource("search-results.txt")!!))
        server.start()

        val results = searchRepository.search("Dark").blockingGet()
        assertEquals(10, results.size)
        (results[0] as SearchResult.Film).run {
            assertEquals(SearchResult.Type.SERIAL, type)
            assertEquals(771383, id)
            assertEquals("Dark", title)
            assertEquals("Dark", originalTitle)
            assertEquals("/13/83/771383/7814585.6.jpg", poster)
            assertEquals(2017, year)
            assertEquals("", description)
        }

        (results[1] as SearchResult.Film).run {
            assertEquals(SearchResult.Type.FILM, type)
            assertEquals(785552, id)
            assertEquals("X-Men: Mroczna Phoenix", title)
            assertEquals("Dark Phoenix", originalTitle)
            assertEquals(2019, year)
            assertEquals("Sophie Turner, James McAvoy", description)
        }
    }
}
