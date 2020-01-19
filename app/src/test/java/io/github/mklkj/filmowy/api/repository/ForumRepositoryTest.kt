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

        val threads = forumRepository.getForumThreadList("/film/tytul-2019-123456", 1).blockingGet()
        assertEquals(2, threads.size)

        with(threads[0]) {
            assertEquals("Atypowy serial dla każdego atypowego fana kina indie", topic)
            assertEquals("z_rozmyslan_przy_sniadan", author)
            assertEquals(936657, authorId)
            assertEquals("/user/z_rozmyslan_przy_sniadan", authorProfileUrl)
            assertEquals("https://ssl-gfx.filmweb.pl/u/34/22/2603422/2603422.2.jpg", authorAvatarUrl)
            assertEquals(LocalDateTime.of(2017, 8, 21, 14, 30, 46), date)
            assertEquals(0, rating)
            assertEquals(2, thumbsUp)
            assertEquals(1, topicAnswers)
            assertEquals("thatschaucerbitch", lastReplayUser)
            assertEquals(2245987, lastReplayUserId)
            assertEquals("/serial/Atypowy-2017-778573/discussion/Atypowy+serial+dla+ka%C5%BCdego+atypowego+fana+kina+indie,2923425#post_15626884", lastReplayUrl)
            assertEquals(LocalDateTime.of(2018, 1, 16, 20, 8, 5), lastReplayDate)
        }
        with(threads[1]) {
            assertEquals(1, topicAnswers)
            assertEquals("krevek", lastReplayUser)
            assertEquals(614890, lastReplayUserId)
            assertTrue(content.startsWith("Uwaga Spoiler"))
            assertEquals("/serial/Atypowy-2017-778573/discussion/Kompakt.,2946315#post_15515262", lastReplayUrl)
            assertEquals(LocalDateTime.of(2017, 11, 21, 15, 50, 48), lastReplayDate)
        }
    }
}
