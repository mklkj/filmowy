package io.github.mklkj.filmowy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mklkj.filmowy.api.NetworkState

abstract class BaseViewModel : ViewModel() {

    open val networkState = MutableLiveData(NetworkState.LOADING)
}
