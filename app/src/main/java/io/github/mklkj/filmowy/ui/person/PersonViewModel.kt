package io.github.mklkj.filmowy.ui.person

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.repository.PersonRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class PersonViewModel @Inject constructor(private val personRepository: PersonRepository) : BaseViewModel() {

    val networkState = MutableLiveData(NetworkState.LOADING)

    fun getPersonInfo(id: Long) = personRepository.getPersonInfoFull(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError {
            Timber.e(it)
            networkState.postValue(NetworkState.error(it.message))
        }
        .onErrorReturnItem(Person(0, "", null, null, null, null, 0, .0, null, false, null, null, -1))
        .doOnSuccess {
            if (it.name.isNotBlank()) networkState.postValue(NetworkState.LOADED)
            else networkState.postValue(NetworkState.error("Wystąpił błąd podczas ładowania osoby :("))
        }
        .toFlowable()
        .toLiveData()

}
