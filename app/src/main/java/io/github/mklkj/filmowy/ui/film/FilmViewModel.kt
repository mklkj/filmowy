package io.github.mklkj.filmowy.ui.film

import androidx.core.net.toUri
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.Resource
import io.github.mklkj.filmowy.api.ajax.FilmVote
import io.github.mklkj.filmowy.api.pojo.FilmFullInfo
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToEpisodesFragment
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToForumFragment
import io.github.mklkj.filmowy.ui.film.FilmFragmentDirections.Companion.actionFilmFragmentToThreadFragment
import kotlinx.coroutines.flow.onEach

class FilmViewModel @ViewModelInject constructor(
    private val userRepository: LoginRepository,
    private val filmRepository: FilmRepository
) : BaseViewModel() {

    val film = MutableLiveData<FilmFullInfo>()

    val vote = MutableLiveData<Resource<FilmVote?>>()

    fun loadData(url: String) {
        loadFilmInfo(url.toUri().path.orEmpty())
        loadUserVote(url.toUri().pathSegments.last().split("-").last().toLong())
    }

    private fun loadFilmInfo(url: String) {
        filmRepository.getFilm(url)
            .handleGlobalStatus()
            .onEachNonNullableData(film::setValue)
            .launchOne("info")
    }

    private fun loadUserVote(filmId: Long) {
        filmRepository.getFilmVote(userRepository.getUser().userId, filmId)
            .handleStatus()
            .onEach { vote.value = it }
            .launchOne("vote")
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
