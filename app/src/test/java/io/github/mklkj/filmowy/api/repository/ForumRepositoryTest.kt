package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDateTime
import retrofit2.create

class ForumRepositoryTest : BaseApiTest() {

    private val forumRepository by lazy { ForumRepository(getRetrofitScrapper().create()) }

    @Test
    fun getFilmForumThreadList() {
        server.enqueue(MockResponse().setBody(getResource("film-forum-thread-list.html")!!))
        server.start()

        val threads = forumRepository.getForumThreadList("https://filmweb/film/tytul-2019-123456", 1).blockingGet()
        assertEquals(3, threads.size)
        with(threads[0]) {
            assertEquals("Historia rodziny", topic)
            assertEquals("Anex", author)
            assertEquals(488269, authorId)
            assertEquals("/user/Anex", authorProfileUrl)
            assertEquals("https://ssl-gfx.filmweb.pl/u/82/69/488269/488269.2.jpg", authorAvatarUrl)
            assertEquals(LocalDateTime.of(2019, 9, 1, 22, 23, 0), date)
            assertEquals(0, rating)
            assertTrue(content.startsWith("Uwaga Spoiler"))
            assertEquals(0, thumbsUp)
            assertEquals(0, topicAnswers)
            assertEquals("", lastReplayUser)
            assertEquals(0, lastReplayUserId)
            assertEquals("", lastReplayUrl)
            assertEquals(null, lastReplayDate)
        }
        with(threads[1]) {
            assertEquals(3, topicAnswers)
            assertEquals("Vegeta88", lastReplayUser)
            assertEquals(1849268, lastReplayUserId)
            assertEquals("/film/Wolverine-2013-515048/discussion/Kto+nast%C4%99pnym+Wolverinem,2766193#post_16254290", lastReplayUrl)
            assertEquals(LocalDateTime.of(2019, 8, 23, 23, 17), lastReplayDate)
        }
    }
}
