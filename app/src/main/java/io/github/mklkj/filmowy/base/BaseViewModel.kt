package io.github.mklkj.filmowy.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import io.github.mklkj.filmowy.api.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

open class BaseViewModel : ViewModel() {

    val navCommand = PublishProcessor.create<NavDirections>()

    val disposable = CompositeDisposable()

    open val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun navigate(directions: NavDirections) {
        navCommand.offer(directions)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
