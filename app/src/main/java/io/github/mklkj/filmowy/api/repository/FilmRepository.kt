package io.github.mklkj.filmowy.api.repository

import com.franmontiel.persistentcookiejar.ClearableCookieJar
import io.github.mklkj.filmowy.api.ApiService
import io.github.mklkj.filmowy.api.ScrapperService
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.ajax.VotesResponse
import io.github.mklkj.filmowy.api.asMethod
import io.github.mklkj.filmowy.api.asVarargMethod
import io.github.mklkj.filmowy.api.exception.NotLoggedInException
import io.github.mklkj.filmowy.api.mapper.*
import io.github.mklkj.filmowy.api.pojo.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import okhttp3.HttpUrl.Companion.toHttpUrl
import org.jsoup.Jsoup
import javax.inject.Inject

class FilmRepository @Inject constructor(
    private val api: ApiService,
    private val scrapper: ScrapperService,
    private val cookieJar: ClearableCookieJar
) {

    fun getFilm(url: String): Single<FilmFullInfo> {
        return scrapper.getFilm(url).map {
            it.also {
                it.url = url
                it.filmInfo = Jsoup.parse(it.filmInfo?.html()?.replace("\"/", "\"app://io.github.mklkj.filmowy/"))
                it.seasonsCount = it.seasons.size
            }
        }
    }

    fun getFilmDescription(filmId: Int): Single<FilmDescription> {
        return api.getWithMethod("getFilmDescription".asMethod(filmId)).map { it.mapFilmDescription() }
    }

//    fun getFilmComments(filmId: Int, page: Int): Single<JsonArray> {
//        return api.getWithMethod("getFilmComments".asMethod(filmId, page * 5, (page + 1) * 5))
//    }

    fun getFilmImages(filmId: Int, page: Int): Single<List<FilmImage>> {
        return api.getWithMethod("getFilmImages".asMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmImages(filmId) }
    }

    fun getFilmInfoFull(filmId: Long): Single<Film> {
        return api.getWithMethod("getFilmInfoFull".asMethod(filmId)).map { it.mapFilmFullInfo(filmId) }
    }

    fun getFilmPersons(filmId: Int, type: FilmPerson.AssocType, page: Int): Single<List<FilmPerson>> {
        return api.getWithMethod("getFilmPersons".asMethod(filmId, type.id, page * 50, 50)).map { it.mapFilmPersons(filmId, type) }
    }

    fun getFilmPersonsLead(filmId: Int, limit: Int): Single<List<FilmPersonsLead>> {
        return api.getWithMethod("getFilmPersonsLead".asMethod(filmId, limit)).map { it.mapFilmPersonsLead(filmId) }
    }

    fun getFilmProfessionCounts(filmId: Int): Single<List<FilmProfessionCount>> {
        return api.getWithMethod("getFilmProfessionCounts".asMethod(filmId)).map { it.mapFilmProfessionCount(filmId) }
    }

    fun getFilmReview(filmId: Int): Single<FilmReview> {
        return api.getWithMethod("getFilmReview".asMethod(filmId)).map { it.mapFilmReview() }
    }

    fun getFilmVideos(filmId: Int, page: Int): Single<List<FilmVideo>> {
        return api.getWithMethod("getFilmVideos".asMethod(filmId, page * 100, (page + 1) * 100)).map { it.mapFilmVideos(filmId) }
    }

    fun getFilmsInfoShort(vararg filmIds: Long): Single<List<Film>> {
        return api.getWithMethod("getFilmsInfoShort".asVarargMethod(*filmIds.toTypedArray())).map { it.mapFilmsInfoShort() }
    }

    fun getFilmsNearestBroadcasts(filmId: Int, page: Int): Single<List<FilmNearestBroadcast>> {
        return api.getWithMethod("getFilmsNearestBroadcasts".asVarargMethod(filmId, page * 100, (page + 1) * 100))
            .map { it.mapFilmsNearestBroadcasts() }
    }

    fun getFilmSeasonEpisodes(url: String, season: Int): Single<List<FilmEpisode>> {
        return scrapper.getSeasonEpisodes(url, season).map { it.mapFilmSeasonEpisodes() }
    }

    fun getFilmEpisodes(url: String): Single<List<FilmEpisode>> {
        return scrapper.getEpisodes(url).map { it.mapFilmSeasonEpisodes() }
    }

    fun getFilmVote(userId: Long, filmId: Long): Maybe<FilmVote> {
        return api.getFilmVote(userId, filmId).flatMapMaybe {
            if (it.size == 1) Maybe.just(it.single())
            else Maybe.empty()
        }
    }

    fun getFilmSeasonUserVotes(filmId: Long, season: Int): Single<VotesResponse.Vote> {
        return api.getUserVotes(filmId, season).map { VotesResponse.Vote(true, it) }.onErrorResumeNext {
            if (it is NotLoggedInException) Single.just(VotesResponse.Vote(false, null))
            else Single.error(it)
        }
    }

    fun voteForSeason(seriesId: Long, season: Int, rate: Int): Completable {
        return scrapper.voteForSeason(
            cookieJar.loadForRequest("https://www.filmweb.pl/".toHttpUrl()).singleOrNull { it.name == "_artuser_token" }?.value.orEmpty(),
            seriesId,
            season,
            rate
        )
    }

    fun voteForEpisode(id: Int, rate: Int): Completable {
        return scrapper.voteForEpisode(
            cookieJar.loadForRequest("https://www.filmweb.pl/".toHttpUrl()).singleOrNull { it.name == "_artuser_token" }?.value.orEmpty(), id, rate
        )
    }
}
