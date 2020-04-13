package io.github.mklkj.filmowy.ui.film

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FilmViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    val film = MutableLiveData<Film>()

    fun navigateToEpisodes(film: Film) = navCommand.offer(FilmFragmentDirections.actionFilmFragmentToEpisodesFragment(film))

    fun navigateToForum(film: Film) = navCommand.offer(FilmFragmentDirections.actionFilmFragmentToForumFragment(film.filmInfo?.forumUrl ?: ""))

    fun loadFilmInfo(id: Long) {
        disposable.add(filmRepository.getFilmInfoFull(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.value = NetworkState.LOADING }
            .subscribe({
                film.postValue(it)
                networkState.value = NetworkState.LOADED
            }) {
                Timber.e(it)
                networkState.value = NetworkState.error(it.message)
            })
    }
}
