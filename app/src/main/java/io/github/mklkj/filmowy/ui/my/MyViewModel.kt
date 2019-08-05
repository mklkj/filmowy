package io.github.mklkj.filmowy.ui.my

import androidx.lifecycle.LiveData
import io.github.mklkj.filmowy.api.pojo.FriendVoteFilmEvent
import io.github.mklkj.filmowy.api.repository.MyRepository
import io.github.mklkj.filmowy.api.toLiveData
import io.github.mklkj.filmowy.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MyViewModel @Inject constructor(private val myRepository: MyRepository) : BaseViewModel() {

    fun getFriendsVotes(): LiveData<List<FriendVoteFilmEvent>> {
        return myRepository.getFriendVoteFilmEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .toLiveData()
    }
}
