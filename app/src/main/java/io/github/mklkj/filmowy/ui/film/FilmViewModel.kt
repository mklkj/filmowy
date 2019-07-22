package io.github.mklkj.filmowy.ui.film

import io.github.mklkj.filmowy.api.repository.FilmRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilmViewModel @Inject constructor(private val filmRepository: FilmRepository) : BaseViewModel() {

    fun getFullFilmInfo(id: Int) = filmRepository.getFilmInfoFull(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .toFlowable()
        .toLiveData()
}
