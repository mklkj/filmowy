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

    @Test
    fun getPersonFilmsLead() {
        server.enqueue(MockResponse().setBody(getResource("person-films-lead.txt")))
        server.start()

        val films = personRepository.getPersonFilmsLead(1, 5).blockingGet()
        assertEquals(5, films.size)
        films[0].run {
            assertEquals(1, personId)
            assertEquals(1, filmType)
            assertEquals(6, assocType)
            assertEquals(699881, filmId)
            assertEquals("Pułkownik Marian Bońka", assocName)
            assertEquals(null, assocAttributes)
            assertEquals("Służby specjalne", filmTitle)
            assertEquals("/98/81/699881/7637158.1.jpg", filmImagePath)
            assertEquals(2014, filmYear)
        }
    }

    @Test
    fun getPersonImages() {
        server.enqueue(MockResponse().setBody(getResource("person-images.txt")))
        server.start()

        val images = personRepository.getPersonImages(1, 0).blockingGet()
        assertEquals(100, images.size)
        images[0].run {
            assertEquals(1, personId)
            assertEquals("/07/71/820771/802695_1.0.jpg", imagePath)
        }
    }

    @Test
    fun getPersonInfoFull() {
        server.enqueue(MockResponse().setBody(getResource("person-info.txt")))
        server.start()

        val info = personRepository.getPersonInfoFull(1).blockingGet()
        info.run {
            assertEquals(1, personId)
            assertEquals("Janusz Chabior", name)
            assertEquals(null, realName)
            assertEquals(of(1963, 2, 17), birthDate)
            assertEquals("Legnica, Polska", birthPlace)
            assertEquals(null, deathDate)
            assertEquals(null, filmKnownFor)
            assertEquals(4463, votesCount)
            assertEquals(8.367466, avgRate, .0)
            assertEquals("/81/52/48152/386756.1.jpg", imagePath)
            assertEquals(true, hasBiography)
            assertEquals(null, filmKnownFor)
            assertEquals(189, height)
            assertEquals(2, sex)
        }
    }
}
