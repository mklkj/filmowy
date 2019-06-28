package io.github.mklkj.filmowy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mklkj.filmowy.api.getPersonFilmsImageUrl
import io.github.mklkj.filmowy.api.pojo.Film
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val filmRepository: FilmRepository) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val film = MutableLiveData<Film>()

    private var index = 1

    fun loadNextFilmInfo() {
        index++
        loadFullFilmInfo()
    }

    fun loadFullFilmInfo() {
        disposable.clear()
        disposable.add(filmRepository.getFilmInfoFull(index)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    film.value = this.copy(imagePath = imagePath?.getPersonFilmsImageUrl(300).toString())
                }
            }) { Timber.e(it) }) // TODO
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
