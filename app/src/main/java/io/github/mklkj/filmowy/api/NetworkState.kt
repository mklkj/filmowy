package io.github.mklkj.filmowy.api

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val message: String? = null
) {
    companion object {
        @JvmStatic val LOADED = NetworkState(Status.SUCCESS)
        @JvmStatic val LOADING = NetworkState(Status.RUNNING)
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }

    enum class Status {
        RUNNING,
        SUCCESS,
        FAILED
    }
}
