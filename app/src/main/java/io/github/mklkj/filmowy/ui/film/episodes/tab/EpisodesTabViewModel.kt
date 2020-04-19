package io.github.mklkj.filmowy.ui.film.episodes.tab

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.encodeName
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class EpisodesTabViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    private lateinit var film: Film

    private var season = 0

    val episodes = MutableLiveData<List<FilmEpisode>>()

    fun loadEpisodes(film: Film, season: Int) {
        this.film = film
        this.season = season
        disposable.add(filmRepository.getFilmSeasonUserVotes(film.filmId, season)
            .flatMap { votes ->
                filmRepository
                    .getFilmSeasonEpisodes(film.encodeName(), season)
                    .map { it ->
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

    fun setVote(film: Film, season: Int, id: Int, rate: Int) {
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
