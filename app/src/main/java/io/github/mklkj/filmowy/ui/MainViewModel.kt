package io.github.mklkj.filmowy.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mklkj.filmowy.api.getPersonFilmsImageUrl
import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(private val filmRepository: FilmRepository) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val text = MutableLiveData("Loading…")

    val imageUrl = MutableLiveData<String>(null)

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
                    imageUrl.value = imagePath?.getPersonFilmsImageUrl(300).toString()
                    text.value = "$index: $this"
                }
            }) {
                Timber.e(it)
                text.value = it.localizedMessage
            })
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}
