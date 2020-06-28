package io.github.mklkj.filmowy.ui.film.episodes.tab

import androidx.core.text.isDigitsOnly
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class EpisodesTabViewModel @ViewModelInject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    private lateinit var film: FilmFullInfo

    private var season = 0

    val episodes = MutableLiveData<List<FilmEpisode>>()

    fun loadEpisodes(film: FilmFullInfo, season: Int) {
        this.film = film
        this.season = season
        disposable.add(filmRepository.getFilmSeasonUserVotes(
            filmId = film.filmId,
            season = if (film.seasonsCount == 1) film.year?.takeIf { it.isDigitsOnly() }?.toInt() ?: 0 else season
        ).flatMap { votes ->
            when (film.seasonsCount) {
                1 -> filmRepository.getFilmEpisodes(film.url)
                else -> filmRepository.getFilmSeasonEpisodes(film.url, season)
            }.map { it ->
                it.map { it.copy(rate = votes.vote?.votes?.getOrElse(it.id.toString()) { -1 } ?: -1) }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.postValue(NetworkState.LOADING) }
            .subscribe({
                episodes.postValue(it)
                networkState.postValue(NetworkState.LOADED)
            }) {
                Timber.e(it)
                networkState.postValue(NetworkState.error(it.message))
            })
    }

    fun markSeasonAsWatched() {
        disposable.add(filmRepository.voteForSeason(film.filmId, season, rate = 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Voted successfully!")
                loadEpisodes(film, season)
            }) {
                Timber.e(it)
            }
        )
    }

    fun setVote(film: FilmFullInfo, season: Int, id: Int, rate: Int) {
        disposable.add(filmRepository.voteForEpisode(id, rate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("Voted successfully!")
                loadEpisodes(film, season)
            }) {
                Timber.e(it)
            }
        )
    }
}
