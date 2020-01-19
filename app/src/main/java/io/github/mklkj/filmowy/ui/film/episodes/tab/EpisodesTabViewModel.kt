package io.github.mklkj.filmowy.ui.film.episodes.tab

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.FilmEpisode
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class EpisodesTabViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    val episodes = MutableLiveData<List<FilmEpisode>>()

    fun loadEpisodes(name: String, season: Int) {
        disposable.add(filmRepository.getFilmSeasonEpisodes(name, season)
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
}