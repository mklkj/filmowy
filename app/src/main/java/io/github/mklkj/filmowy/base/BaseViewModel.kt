package io.github.mklkj.filmowy.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.hadilq.liveevent.LiveEvent
import io.github.mklkj.filmowy.api.NetworkState
import io.github.mklkj.filmowy.api.Resource
import io.github.mklkj.filmowy.api.Status
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.time.LocalDateTime.now

open class BaseViewModel : ViewModel() {

    val status = LiveEvent<Status>()

    private val jobs = mutableMapOf<String, Job>()

    val navCommand = PublishProcessor.create<NavDirections>()

    @Deprecated("use coroutines instead")
    val disposable = CompositeDisposable()

    open val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    fun navigate(directions: NavDirections) {
        navCommand.offer(directions)
    }

    fun <T> Flow<T>.launchOne(individualJobTag: String = "load"): Job {
        jobs[individualJobTag]?.cancel()
        val job = launchIn(viewModelScope)
        jobs[individualJobTag] = job
        return job
    }

    fun <T> Flow<T>.launchIn(): Job {
        return launchIn(viewModelScope)
    }

    fun <T> Flow<Resource<T>>.handleGlobalStatus() = onEach {
        Timber.tag(this@BaseViewModel.javaClass.simpleName)
            .i("handleGlobalStatus: ${it.status} ${now().nano}")
        status.value = it.status
        if (it.error != null) processError(it.error, it.data != null)
    }

    fun <T> Flow<Resource<T>>.handleStatus() = onEach {
        Timber.tag(this@BaseViewModel.javaClass.simpleName)
            .i("handleStatus: ${it.status} ${now().nano}")
        if (it.error != null) processError(it.error, it.data != null)
    }

    fun <T> Flow<Resource<T>>.onEachNonNullableData(callback: (T) -> Unit) = onEach {
        it.data?.let(callback)
    }

    private fun processError(error: Throwable, showInToast: Boolean) {
//        if (showInToast) toastMessages.value = error.message
//        messages.value = error.message
        Timber.e(error)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
