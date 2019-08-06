package io.github.mklkj.filmowy.ui.film

import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FilmViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    fun getFullFilmInfo(id: Long) = filmRepository.getFilmInfoFull(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorReturnItem(Film.get(-1))
        .doOnError {
            Timber.e(it)
            networkState.postValue(NetworkState.error(it.message))
        }
        .doOnSuccess {
            if (!it.title.isBlank()) networkState.postValue(NetworkState.LOADED)
            else networkState.postValue(NetworkState.error("Wystąpił błąd podczas ładowania filmu :("))
        }
        .toFlowable()
        .toLiveData()
}
