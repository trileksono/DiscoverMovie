package com.tleksono.discovermovie.domain.model

data class State<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T): State<T> {
            return State(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): State<T> {
            return State(Status.ERROR, data, message)
        }
    }
}
