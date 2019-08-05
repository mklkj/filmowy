package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDateTime.of
import retrofit2.create

class MyRepositoryTest : BaseApiTest() {

    private val myRepository by lazy { MyRepository(getRetrofit().create()) }

    @Test
    fun getFriendVoteFilmEvents() {
        server.enqueue(MockResponse().setBody(getResource("friend-vote-film-events.txt")!!))
        server.start()

        val votes = myRepository.getFriendVoteFilmEvents(0).blockingGet()
        votes[0].run {
            assertEquals(831, filmId)
            assertEquals(-1, userId)
            assertEquals(4369325, friendUserId)
            assertEquals("film", filmType)
            assertEquals("Toy Story 2", filmTitle)
            assertEquals(0, otherFilmsCount)
            assertEquals(of(2019, 8, 2, 10, 50, 19), datetime)

            userFilmVote.run {
                assertEquals(831, filmId)
                assertEquals(-1, userId)
                assertEquals(4369325, friendUserId)
                assertEquals(6, rate)
                assertEquals(null, comment)
                assertEquals(0, commentsCount)
                assertEquals(0, likesCount)
                assertEquals(false, isLike)
                assertEquals(of(2019, 8, 2, 10, 50, 19), datetime)
            }
        }
    }
}
