package com.tleksono.discovermovie.common.ext

fun String.buildPosterUrl(): String {
    return "https://image.tmdb.org/t/p/w342".plus(this)
}

fun String.buildBackdropUrl(): String {
    return "https://image.tmdb.org/t/p/original".plus(this)
}

fun String.buildImageYoutubeUrl(): String {
    return String.format("https://img.youtube.com/vi/%s/hqdefault.jpg", this)
}

fun String?.getYearReleaseDate(): String? {
    return this?.substring(0, 4).orEmpty()
}
