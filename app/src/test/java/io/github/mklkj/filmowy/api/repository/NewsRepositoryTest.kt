package io.github.mklkj.filmowy.api.repository

import android.content.SharedPreferences
import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.threeten.bp.LocalDateTime.of
import retrofit2.create

class NewsRepositoryTest : BaseApiTest() {

    @Mock
    private lateinit var preferences: SharedPreferences

    private val newsRepository by lazy { NewsRepository(getRetrofit().create(), getRetrofitScrapper().create(), preferences) }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getNewsList() {
        server.enqueue(MockResponse().setBody(getResource("news-list.txt")!!))
        server.start()

        val news = newsRepository.getNewsList(1).blockingGet()
        assertEquals(20, news.size)
        news[0].run {
            assertEquals(133563, id)
            assertEquals("Morena Baccarin stawi czoła kataklizmowi u boku Gerarda Butlera", title)
            assertEquals("Aktorka dołączyła do obsady thrillera \"Greenland\".", lead)
            assertEquals(of(2019, 6, 19, 10, 9, 19), publicationTime)
            assertEquals("/np/1985708/2019/20253_1.1.jpg", newsImageUrl)
            assertEquals("news", type)
        }
    }

    @Test
    fun getNews() {
        server.enqueue(MockResponse().setBody(getResource("news.txt")!!))
        server.start()

        val news = newsRepository.getArticle(1).blockingGet()
        news.run {
            assertEquals("Najlepsze filmy i seriale o podróżach w czasie na Netflix", title)
            assertEquals("Przedstawiamy ranking najlepszych produkcji o podróżach w czasie z okazji premiery 2. sezonu \"Dark\".", lead)
            assertTrue(content.startsWith("Premiera drugiego sezonu"))
            assertEquals(of(2019, 6, 19, 11, 0, 0), publicationTime)
            assertEquals("/np/1032473/2019/20265_1.1.jpg", newsImageUrl)
            assertEquals(1, commentsCount)
            assertEquals(null, source)
            assertEquals(null, author)
        }
    }

    @Test(expected = Exception::class)
    fun getNews_null() {
        server.enqueue(MockResponse().setBody(getResource("null.txt")!!))
        server.start()

        newsRepository.getArticle(1).blockingGet()
    }

    @Test
    fun getNews_scrapper() {
        server.enqueue(MockResponse().setBody(getResource("news.txt")!!))
        server.enqueue(MockResponse().setBody(getResource("news.html")!!))
        server.start()

        `when`(preferences.getBoolean(anyString(), anyBoolean())).thenReturn(true)

        val news = newsRepository.getArticle(1, "Najlepsze filmy i seriale o podróżach w czasie na Netflix").blockingGet()
        news.run {
            assertTrue(contentHtml.startsWith("Premiera drugiego sezonu\n<b> \" <a class=\"internal\"")) //

            assertEquals("Najlepsze filmy i seriale o podróżach w czasie na Netflix", title)
            assertEquals("Przedstawiamy ranking najlepszych produkcji o podróżach w czasie z okazji premiery 2. sezonu \"Dark\".", lead)
            assertTrue(content.startsWith("Premiera drugiego sezonu"))
            assertEquals(of(2019, 6, 19, 11, 0, 0), publicationTime)
            assertEquals("/np/1032473/2019/20265_1.1.jpg", newsImageUrl)
            assertEquals(1, commentsCount)
            assertEquals(null, source)
            assertEquals(null, author)
        }
    }

    @Test
    fun getNewsComments() {
        server.enqueue(MockResponse().setBody(getResource("news-comments.txt")!!))
        server.start()

        val comments = newsRepository.getNewsComments(1, 0).blockingGet()
        assertEquals(2, comments.size)
        comments[0].run {
            assertEquals(1, newsId)
            assertEquals(1013634, userId)
            assertEquals("/98/64/2439864/2439864.0.jpg", userPhoto)
            assertEquals("Tiw", userName)
            assertEquals("Dobry serial byl, w przeciwienstwie do wielu innych na tej platformie", comment)
            assertEquals(of(2019, 6, 19, 19, 37, 36), time)
        }
    }
}
