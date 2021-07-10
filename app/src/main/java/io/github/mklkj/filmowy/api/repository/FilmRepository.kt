package io.github.mklkj.filmowy.api.repository

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.ajax.VotesResponse
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.asVarargMethod
import io.github.mklkj.filmowy.api.exception.NotLoggedInException
import io.github.mklkj.filmowy.api.mapper.*
import io.github.mklkj.filmowy.api.pojo.*
import io.github.mklkj.filmowy.utils.flowWithResource
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val api: ApiService,
    private val scrapper: ScrapperService,
    private val cookieJar: ClearableCookieJar
) {

    fun getFilm(url: String) = flowWithResource {
        scrapper.getFilm(url).also {
            it.filmId = url.split("-").last().toLong()
            it.url = url
            it.filmInfo = Jsoup.parse(it.filmInfo?.html()?.replace("\"/", "\"app://io.github.mklkj.filmowy/"))
            it.seasonsCount = it.seasons.size
        }
    }

    suspend fun getFilmDescription(filmId: Int): FilmDescription {
        return api.getWithMethod("getFilmDescription".asMethod(filmId)).mapFilmDescription()
    }

//    fun getFilmComments(filmId: Int, page: Int): Single<JsonArray> {
//        return api.getWithMethod("getFilmComments".asMethod(filmId, page * 5, (page + 1) * 5))
//    }

    suspend fun getFilmImages(filmId: Int, page: Int): List<FilmImage> {
        return api.getWithMethod("getFilmImages".asMethod(filmId, page * 100, (page + 1) * 100)).mapFilmImages(filmId)
    }

    suspend fun getFilmInfoFull(filmId: Long): Film {
        return api.getWithMethod("getFilmInfoFull".asMethod(filmId)).mapFilmFullInfo(filmId)
    }

    suspend fun getFilmPersons(filmId: Int, type: FilmPerson.AssocType, page: Int): List<FilmPerson> {
        return api.getWithMethod("getFilmPersons".asMethod(filmId, type.id, page * 50, 50)).mapFilmPersons(filmId, type)
    }

    suspend fun getFilmPersonsLead(filmId: Int, limit: Int): List<FilmPersonsLead> {
        return api.getWithMethod("getFilmPersonsLead".asMethod(filmId, limit)).mapFilmPersonsLead(filmId)
    }

    suspend fun getFilmProfessionCounts(filmId: Int): List<FilmProfessionCount> {
        return api.getWithMethod("getFilmProfessionCounts".asMethod(filmId)).mapFilmProfessionCount(filmId)
    }

    suspend fun getFilmReview(filmId: Int): FilmReview {
        return api.getWithMethod("getFilmReview".asMethod(filmId)).mapFilmReview()
    }

    suspend fun getFilmVideos(filmId: Int, page: Int): List<FilmVideo> {
        return api.getWithMethod("getFilmVideos".asMethod(filmId, page * 100, (page + 1) * 100)).mapFilmVideos(filmId)
    }

    suspend fun getFilmsInfoShort(vararg filmIds: Long): List<Film> {
        return api.getWithMethod("getFilmsInfoShort".asVarargMethod(*filmIds.toTypedArray())).mapFilmsInfoShort()
    }

    suspend fun getFilmsNearestBroadcasts(filmId: Int, page: Int): List<FilmNearestBroadcast> {
        return api.getWithMethod("getFilmsNearestBroadcasts".asVarargMethod(filmId, page * 100, (page + 1) * 100)).mapFilmsNearestBroadcasts()
    }

    suspend fun getFilmSeasonEpisodes(url: String, season: Int): List<FilmEpisode> {
        return scrapper.getSeasonEpisodes(url, season).mapFilmSeasonEpisodes()
    }

    suspend fun getFilmEpisodes(url: String): List<FilmEpisode> {
        return scrapper.getEpisodes(url).mapFilmSeasonEpisodes()
    }

    fun getFilmVote(userId: Long, filmId: Long) = flowWithResource {
        api.getFilmVote(userId, filmId).let {
            if (it.size == 1) it.single()
            else null
        }
    }

    fun getFilmSeasonUserVotes(filmId: Long, season: Int) = flowWithResource {
        try {
            VotesResponse.Vote(true, api.getUserVotes(filmId, season))
        } catch (e: NotLoggedInException) {
            VotesResponse.Vote(false, null)
        }
    }

    fun voteForSeason(seriesId: Long, season: Int, rate: Int) = flowWithResource {
        scrapper.voteForSeason(
            cookieJar.loadForRequest("https://www.filmweb.pl/".toHttpUrl()).singleOrNull { it.name == "_artuser_token" }?.value.orEmpty(),
            seriesId,
            season,
            rate
        )
    }

    fun voteForEpisode(id: Int, rate: Int) = flowWithResource {
        scrapper.voteForEpisode(
            cookieJar.loadForRequest("https://www.filmweb.pl/".toHttpUrl()).singleOrNull { it.name == "_artuser_token" }?.value.orEmpty(), id, rate
        )
    }
}
