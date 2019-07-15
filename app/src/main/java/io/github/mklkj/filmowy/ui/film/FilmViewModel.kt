package io.github.mklkj.filmowy.ui.film

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.ui.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class FilmViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    val film = MutableLiveData<Film>()

    private var index = 1

    init {
        loadFullFilmInfo()
    }

    fun loadNextFilmInfo() {
        index++
        loadFullFilmInfo()
    }

    private fun loadFullFilmInfo() {
        disposable.clear()
        disposable.add(filmRepository.getFilmInfoFull(index)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                film.postValue(it)
            }) { Timber.e(it) }) // TODO
    }
}
