package io.github.mklkj.filmowy.ui.film

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToEpisodesFragment
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToForumFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FilmViewModel @ViewModelInject constructor(
    private val userRepository: LoginRepository,
    private val filmRepository: FilmRepository
) : BaseViewModel() {

    val film = MutableLiveData<Film>()

    val vote = MutableLiveData<FilmVote>()

    fun loadData(filmId: Long) {
        loadFilmInfo(filmId)
        loadUserVote(filmId)
    }

    private fun loadFilmInfo(id: Long) {
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

    private fun loadUserVote(filmId: Long) {
        disposable.add(Single.fromCallable { userRepository.getUser() }
            .flatMapMaybe { filmRepository.getFilmVote(it.userId, filmId) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                vote.value = it
            }, {
                Timber.e(it)
            }, {
                Timber.d("loading vote complete")
            }))
    }

    fun navigateToEpisodes(film: Film) {
        navigate(actionFilmFragmentToEpisodesFragment(film))
    }

    fun navigateToForum(film: Film) {
        navigate(actionFilmFragmentToForumFragment(film.filmInfo?.forumUrl.orEmpty()))
    }
}
