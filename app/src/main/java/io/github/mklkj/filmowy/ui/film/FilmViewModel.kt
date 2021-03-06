package io.github.mklkj.filmowy.ui.film

import androidx.core.net.toUri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.Resource
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToEpisodesFragment
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToForumFragment
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToThreadFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FilmViewModel @ViewModelInject constructor(
    private val userRepository: LoginRepository,
    private val filmRepository: FilmRepository
) : BaseViewModel() {

    val film = MutableLiveData<FilmFullInfo>()

    val vote = MutableLiveData<Resource<FilmVote>>()

    fun loadData(url: String) {
        loadFilmInfo(url.toUri().path.orEmpty())
        loadUserVote(url.toUri().pathSegments.last().split("-").last().toLong())
    }

    private fun loadFilmInfo(url: String) {
        disposable.add(filmRepository.getFilm(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.value = NetworkState.LOADING }
            .subscribe({
                film.value = it
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
            .doOnSubscribe { vote.value = Resource.loading() }
            .subscribe({
                vote.value = Resource.success(it)
            }, {
                vote.value = Resource.error(it)
                Timber.e(it)
            }, {
                vote.value = Resource.success(null)
                Timber.d("loading vote complete")
            }))
    }

    fun navigateToEpisodes() {
        navigate(actionFilmFragmentToEpisodesFragment(film.value!!))
    }

    fun navigateToTopic(topic: FilmFullInfo.ForumTopic) {
        navigate(actionFilmFragmentToThreadFragment(topic.url))
    }

    fun navigateToForum() {
        navigate(actionFilmFragmentToForumFragment(film.value?.forumUrl.orEmpty()))
    }
}
