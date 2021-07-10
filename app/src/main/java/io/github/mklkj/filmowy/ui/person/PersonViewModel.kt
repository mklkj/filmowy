package io.github.mklkj.filmowy.ui.person

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.Person
import io.github.mklkj.filmowy.api.repository.PersonRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import kotlinx.coroutines.flow.onEach

class PersonViewModel @ViewModelInject constructor(private val personRepository: PersonRepository) : BaseViewModel() {

    val person = MutableLiveData<Person>()

    fun getPersonInfo(id: Long) {
        personRepository.getPersonInfoFull(id)
            .handleGlobalStatus()
            .onEach { person.value = it.data }
            .launchOne()
    }
}
