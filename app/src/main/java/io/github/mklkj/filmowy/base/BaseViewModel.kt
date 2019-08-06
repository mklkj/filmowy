package io.github.mklkj.filmowy.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mklkj.filmowy.api.NetworkState
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val disposable = CompositeDisposable()

    open val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
