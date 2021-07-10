package io.github.mklkj.filmowy.utils

import io.github.mklkj.filmowy.api.Resource
import kotlinx.coroutines.flow.flow

fun <T> flowWithResource(block: suspend () -> T) = flow {
    emit(Resource.loading())
    try {
        emit(Resource.success(block()))
    } catch (e: Throwable) {
        emit(Resource.error(e))
    }
}
