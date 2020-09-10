package com.tleksono.discovermovie.common.ext

import com.tleksono.discovermovie.domain.model.State
import retrofit2.Call

fun <T, R> Call<T>.safeExecute(transform: (T) -> R): State<R> {
    return try {
        val response = this.execute()
        when (response.isSuccessful) {
            true -> State.success(transform(response.body()!!))
            false -> State.error(response.errorBody()?.string().parseError())
        }
    } catch (e: Throwable) {
        e.printStackTrace()
        State.error(e.parseError(), null)
    }
}
