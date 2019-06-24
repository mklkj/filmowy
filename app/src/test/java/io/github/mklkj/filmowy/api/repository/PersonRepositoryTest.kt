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

    @Test
    fun getPersonFilms() {
        server.enqueue(MockResponse().setBody(getResource("person-films.txt")))
        server.start()

        val films = personRepository.getPersonFilms(1, 1, 1, 1, 1).blockingGet()
        assertEquals(5, films.size)
        films[0].run {
            assertEquals(820771, filmId)
            assertEquals("Ryszard Nowak \"Rysiek\"", assocName)
            assertEquals("Odwróceni. Ojcowie i córki", filmTitle)
            assertEquals("/07/71/820771/7877148.1.jpg", filmImagePath)
            assertEquals(2019, filmYear)
            assertEquals(null, assocAttributes)
            assertEquals(null, originalFilmTitle)
        }
    }
}
