package io.github.mklkj.filmowy.api.repository

import io.github.mklkj.filmowy.api.BaseApiTest
import io.github.mklkj.filmowy.api.pojo.FilmPerson
import okhttp3.JavaNetCookieJar
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import retrofit2.create
import java.net.CookieManager

class FilmRepositoryTest : BaseApiTest() {

    private val filmRepository by lazy { FilmRepository(getRetrofit().create(), getRetrofitScrapper().create(), JavaNetCookieJar(CookieManager())) }

    @Test
    fun getFilmDescription() {
        server.enqueue(MockResponse().setBody(getResource("film-description.txt")!!))
        server.start()

        val desc = filmRepository.getFilmDescription(1).blockingGet()
        assertEquals("W systemie edukacji znajdującym się na krawędzi upadku, jeden człowiek został doprowadzony do ostateczności.", desc.description)
    }

    @Test
    fun getFilmImages() {
        server.enqueue(MockResponse().setBody(getResource("film-images.txt")!!))
        server.start()

        val images = filmRepository.getFilmImages(1, 5).blockingGet()
        assertEquals(50, images.size)
        images[0].run {
            assertEquals(1, filmId)
            assertEquals("/16/34/771634/708447.0.jpg", imagePath)
        }
    }

    @Test
    fun getFilmInfoFull_noVideos() {
        server.enqueue(MockResponse().setBody(getResource("film-info-full_no-videos.txt")!!))
        server.start()

        val info = filmRepository.getFilmInfoFull(2).blockingGet()
        info.run {
            assertEquals("Paragraf 187", title)
            assertEquals(7.32363, avgRate, .0)
            assertEquals(7796, votesCount)
            assertEquals(4136, wantSee)
            assertEquals(1997, year)
            assertEquals(120, duration)
            assertEquals("/00/01/1/7003079.2.jpg", imagePath)

            filmInfo?.run {
                assertEquals("One Eight Seven", originalTitle)
                assertEquals("Thriller", genres)
                assertEquals("0", commentsCount)
                assertEquals("https://www.filmweb.pl/film/Paragraf+187-1997-1/discussion", forumUrl)
                assertEquals(true, hasReview)
                assertEquals(true, hasDescription)
                assertEquals(null, videoImageUrl)
                assertEquals(null, videoUrl)
                assertEquals(null, videoHDUrl)
                assertEquals(null, video480pUrl)
                assertEquals(null, ageRestriction)
                assertEquals("1997-07-30", premiereWorld)
                assertEquals("1998-02-27", premiereCountry)
                assertEquals(0, filmType)
                assertEquals(0, seasonsCount)
                assertEquals(0, episodesCount)
                assertEquals("USA", countriesString)
                assertEquals("Napadnięty nauczyciel postanawia wrócić do pracy w innej szkole, aby walczyć z agresją i przemocą.", synopsis)
                assertEquals(false, recommends)
                assertEquals(LocalDate.of(1997, 7, 30), premiereWorldPublic)
                assertEquals(LocalDate.of(1998, 2, 27), premiereCountryPublic)
            }
        }
    }

    @Test
    fun getFilmPersons() {
        server.enqueue(MockResponse().setBody(getResource("film-persons.txt")!!))
        server.start()

        val persons = filmRepository.getFilmPersons(2, FilmPerson.AssocType.ACTOR, 0).blockingGet()
        persons.run {
            assertEquals(1, size)
        }
    }

    @Test
    fun getFilmPersons_null() {
        server.enqueue(MockResponse().setBody(getResource("exc-npe.txt")!!))
        server.start()

        val persons = filmRepository.getFilmPersons(2, FilmPerson.AssocType.ACTOR, 0).blockingGet()
        assertEquals(0, persons.size)
    }

    @Test
    fun getFilmPersonsLead() {
        server.enqueue(MockResponse().setBody(getResource("film-persons-lead.txt")!!))
        server.start()

        val persons = filmRepository.getFilmPersonsLead(3, 50).blockingGet()
        assertEquals(15, persons.size)
        persons[0].run {
            assertEquals(3, filmId)
            assertEquals(606018, personId)
            assertEquals(null, assocAttributes)
            assertEquals(null, assocName)
            assertEquals(FilmPerson.AssocType.DIRECTOR, assocType)
            assertEquals("Reed Morano", personName)
            assertEquals("/60/18/606018/291795.1.jpg", personImagePath)
        }
    }

    @Test
    fun getFilmProfessionCounts() {
        server.enqueue(MockResponse().setBody(getResource("film-profession-counts.txt")!!))
        server.start()

        val professions = filmRepository.getFilmProfessionCounts(3).blockingGet()
        assertEquals(8, professions.size)
        professions[0].run {
            assertEquals(3, filmId)
            assertEquals(7, assocCount)
            assertEquals(FilmPerson.AssocType.DIRECTOR, assocType)
        }
    }

    @Test
    fun getFilmReview() {
        server.enqueue(MockResponse().setBody(getResource("film-review.txt")!!))
        server.start()

        val review = filmRepository.getFilmReview(3).blockingGet()
        review.run {
            assertEquals("SQNboy", authorName)
            assertEquals(163588, authorUserId)
            assertEquals("/35/88/163588/163588.0.jpg", authorImagePath)
            assertTrue(content.startsWith("\"Paragraf 187\" to dowód na to, że"))
            assertEquals(3533, content.length)
            assertEquals("Szkolna rzeczywistość", title)
        }
    }

    @Test
    fun getFilmVideos() {
        server.enqueue(MockResponse().setBody(getResource("film-videos.txt")!!))
        server.start()

        val videos = filmRepository.getFilmVideos(4, 0).blockingGet()
        assertEquals(1, videos.size)
        videos[0].run {
            assertEquals(4, filmId)
            assertEquals("https://1.fwcdn.pl/wv/34/10/13410/snap2.13410.1.jpg", imagePath)
            assertEquals("https://mm.filmweb.pl/2/the_devil_s_advocate__1997____official_movie_trailer.iphone.mp4", videoPath)
        }
    }

    @Test
    fun getFilmVideos_empty() {
        server.enqueue(MockResponse().setBody(getResource("empty.txt")!!))
        server.start()

        val videos = filmRepository.getFilmVideos(5, 0).blockingGet()
        assertEquals(0, videos.size)
    }

    @Test
    fun getFilmsInfoShort() {
        server.enqueue(MockResponse().setBody(getResource("film-info-short.txt")!!))
        server.start()

        val infos = filmRepository.getFilmsInfoShort(1, 2).blockingGet()
        assertEquals(1, infos.size)
        infos[0].run {
            assertEquals("Paragraf 187", title)
            assertEquals(1997, year)
            assertEquals(7.32566, avgRate, .0)
            assertEquals(7689, votesCount)
            assertEquals(120, duration)
            assertEquals("/00/01/1/7003079.2.jpg", imagePath)
            assertEquals(null, filmInfo)
        }
    }

    @Test
    fun getFilmsNearestBroadcasts() {
        server.enqueue(MockResponse().setBody(getResource("film-nearest-broadcasts.txt")!!))
        server.start()

        val broadcasts = filmRepository.getFilmsNearestBroadcasts(1, 0).blockingGet()
        assertEquals(3, broadcasts.size)
        broadcasts[0].run {
            assertEquals(9, filmId)
            assertEquals(29, tvChannelId)
            assertEquals(LocalDateTime.of(2019, 6, 29, 17, 55, 0), dateTime)
            assertEquals(61600719, id)
            assertEquals("film SF", description)
        }
    }

    @Test
    fun getFilmSeasonEpisodes() {
        server.enqueue(MockResponse().setBody(getResource("film-season-episodes.html")!!))
        server.start()

        val episodes = filmRepository.getFilmSeasonEpisodes("Nazwa", 1).blockingGet()
        assertEquals(2, episodes.size)
        episodes[0].run {
            assertEquals(1659645, id)
            assertEquals(4, season)
            assertEquals(1, number)
            assertEquals(LocalDate.of(2014, 9, 23), premiereDate)
            assertEquals("Panopticon", title)
            assertEquals("8,1", avgRate)
        }
        episodes[1].run {
            assertEquals(1703094, id)
            assertEquals(4, season)
            assertEquals(2, number)
            assertEquals(LocalDate.of(2014, 9, 30), premiereDate)
            assertEquals("Nautilus", title)
            assertEquals("8,1", avgRate)
        }
    }
}
