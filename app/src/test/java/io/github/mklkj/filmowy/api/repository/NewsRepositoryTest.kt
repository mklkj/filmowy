package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDateTime.of
import retrofit2.create

class NewsRepositoryTest : BaseApiTest() {

    private val newsRepository by lazy { NewsRepository(getRetrofit().create()) }

    @Test
    fun getNewsList() {
        server.enqueue(MockResponse().setBody(getResource("news-list.txt")))
        server.start()

        val news = newsRepository.getNewsList(1).blockingGet()
        assertEquals(20, news.size)
        news[0].run {
            assertEquals(133563, id)
            assertEquals("Morena Baccarin stawi czoła kataklizmowi u boku Gerarda Butlera", title)
            assertEquals("Aktorka dołączyła do obsady thrillera \"Greenland\".", lead)
            assertEquals(of(2019, 6, 19, 12, 9, 19), publicationTime)
            assertEquals("/np/1985708/2019/20253_1.1.jpg", newsImageUrl)
            assertEquals("news", type)
        }
    }

    @Test
    fun getNews() {
        server.enqueue(MockResponse().setBody(getResource("news.txt")))
        server.start()

        val news = newsRepository.getNews(1).blockingGet()
        news.run {
            assertEquals("Najlepsze filmy i seriale o podróżach w czasie na Netflix", title)
            assertEquals("Przedstawiamy ranking najlepszych produkcji o podróżach w czasie z okazji premiery 2. sezonu \"Dark\".", lead)
            assertTrue(content.startsWith("Premiera drugiego sezonu"))
            assertEquals(of(2019, 6, 19, 13, 0, 0), publicationTime)
            assertEquals("/np/1032473/2019/20265_1.1.jpg", newsImageUrl)
            assertEquals(1, commentsCount)
            assertEquals(null, source)
            assertEquals(null, author)
        }
    }

    @Test(expected = Exception::class)
    fun getNews_null() {
        server.enqueue(MockResponse().setBody(getResource("null.txt")))
        server.start()

        newsRepository.getNews(1).blockingGet()
    }
}