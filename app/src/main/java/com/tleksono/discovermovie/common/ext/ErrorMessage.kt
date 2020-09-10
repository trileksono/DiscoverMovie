package com.tleksono.discovermovie.common.ext

import com.google.gson.Gson
import com.tleksono.discovermovie.common.NoInternetException
import retrofit2.HttpException

fun Throwable.parseError(): String {
    return if (this is HttpException) {
        this.message().parseError()
    } else if (this is NoInternetException) {
        "Koneksi bermasalah, periksa koneksi dan coba lagi nanti"
    } else {
        this.message ?: "Terjadi kesalahan, coba lagi nanti"
    }
}

fun String?.parseError(): String {
    val gson = Gson()
    val response = gson.fromJson(this, Map::class.java)
    return "${response["status_message"] ?: "Terjadi kesalahan, coba lagi nanti"}"
}
