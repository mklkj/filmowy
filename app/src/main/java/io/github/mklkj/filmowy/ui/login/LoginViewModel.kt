package io.github.mklkj.filmowy.ui.login

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.pojo.UserData
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : BaseViewModel() {

    var email = MutableLiveData<String>()

    var password = MutableLiveData<String>()

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