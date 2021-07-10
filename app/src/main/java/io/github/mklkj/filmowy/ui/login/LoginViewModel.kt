package io.github.mklkj.filmowy.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import kotlinx.coroutines.flow.onEach

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) : BaseViewModel() {

    var email = MutableLiveData("")

    var password = MutableLiveData("")

    val user = MutableLiveData<UserData>()

    fun login() {
        loginRepository.login(email.value, password.value)
            .handleGlobalStatus()
            .onEach {
                loginRepository.saveUser(it.data!!)
                user.value = it.data
            }
            .launchOne("login")
    }
}
