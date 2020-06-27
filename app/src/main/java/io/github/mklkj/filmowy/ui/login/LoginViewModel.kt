package io.github.mklkj.filmowy.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class LoginViewModel @ViewModelInject constructor(private val loginRepository: LoginRepository) : BaseViewModel() {

    var email = MutableLiveData("")

    var password = MutableLiveData("")

    val user = MutableLiveData<UserData>()

    fun login() {
        disposable.add(loginRepository.login(email.value, password.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { networkState.value = NetworkState.LOADING }
            .subscribe({
                loginRepository.saveUser(it)
                user.value = it
                networkState.value = NetworkState.LOADED
            }) {
                networkState.value = NetworkState.error(it.localizedMessage)
                Timber.e(it)
            }
        )
    }
}
