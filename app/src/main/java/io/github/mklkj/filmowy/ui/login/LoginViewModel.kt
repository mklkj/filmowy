package io.github.mklkj.filmowy.ui.login

import androidx.lifecycle.MutableLiveData
import io.github.mklkj.filmowy.api.repository.LoginRepository
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val disposable = CompositeDisposable()

    var email: MutableLiveData<String> = MutableLiveData()

    var password: MutableLiveData<String> = MutableLiveData()

    fun login() {
        disposable.add(
            loginRepository.login(email.value, password.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Timber.d(it.toString())
                    loginRepository.saveUser(it)
                }) { Timber.e(it) }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
