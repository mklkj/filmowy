package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDate.of
import retrofit2.create

class PersonRepositoryTest : BaseApiTest() {

    private val personRepository by lazy { PersonRepository(getRetrofit().create()) }

    @Test
    fun getFilmDescription() {
        server.enqueue(MockResponse().setBody(getResource("persons-born-today.txt")))
        server.start()

        val birthdays = personRepository.getBornTodayPersons().blockingGet()
        assertEquals(25, birthdays.size)
        birthdays[0].run {
            assertEquals(109, id)
            assertEquals("Meryl Streep", name)
            assertEquals("/01/09/109/449960.1.jpg", poster)
            assertEquals(of(1949, 6, 22), birthDate)
            assertEquals(null, deathDate)
        }
    }

    @Test
    fun getPersonBiography() {
        server.enqueue(MockResponse().setBody(getResource("person-biography.txt")))
        server.start()

        val biography = personRepository.getPersonBiography(1).blockingGet()
        assertEquals(6193, biography.biography.length)
    }
}
