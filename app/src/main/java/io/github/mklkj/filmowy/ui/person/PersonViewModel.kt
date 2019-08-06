package io.github.mklkj.filmowy.ui.person

import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.repository.PersonRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) : BaseViewModel() {

    fun getPersonInfo(id: Long) = personRepository.getPersonInfoFull(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError {
            Timber.e(it)
            networkState.postValue(NetworkState.error(it.message))
        }
        .onErrorReturnItem(Person.get(-1))
        .doOnSuccess {
            if (it.name.isNotBlank()) networkState.postValue(NetworkState.LOADED)
            else networkState.postValue(NetworkState.error("Wystąpił błąd podczas ładowania osoby :("))
        }
        .toFlowable()
        .toLiveData()

}
