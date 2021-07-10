package io.github.mklkj.filmowy.ui.film.episodes.tab

import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.github.mklkj.filmowy.utils.flowWithResource
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class EpisodesTabViewModel @ViewModelInject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    private lateinit var film: FilmFullInfo

    private var season = 0

    val episodes = MutableLiveData<List<FilmEpisode>>()

    fun loadEpisodes(film: FilmFullInfo, season: Int) {
        this.film = film
        this.season = season

        filmRepository.getFilmSeasonUserVotes(
            filmId = film.filmId,
            season = if (film.seasonsCount == 1) film.year?.takeIf { it.isDigitsOnly() }?.toInt() ?: 0 else season
        ).flatMapMerge { votes ->
            flowWithResource {
                when (film.seasonsCount) {
                    1 -> filmRepository.getFilmEpisodes(film.url)
                    else -> filmRepository.getFilmSeasonEpisodes(film.url, season)
                }.map {
                    it.copy(rate = votes.data?.vote?.votes?.getOrElse(it.id.toString()) { -1 } ?: -1)
                }
            }
        }.handleGlobalStatus()
            .onEach { episodes.postValue(it.data) }
            .launchOne()
    }

    fun markSeasonAsWatched() {
        filmRepository.voteForSeason(film.filmId, season, rate = 0)
            .handleStatus()
            .onEach {
                Timber.d("Voted successfully!")
                loadEpisodes(film, season)
            }
            .launchIn()
    }

    fun setVote(film: FilmFullInfo, season: Int, id: Int, rate: Int) {
        filmRepository.voteForEpisode(id, rate)
            .handleStatus()
            .onEach {
                Timber.d("Voted successfully!")
                loadEpisodes(film, season)
            }
            .launchIn()
    }
}
