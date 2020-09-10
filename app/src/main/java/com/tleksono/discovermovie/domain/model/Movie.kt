package com.tleksono.discovermovie.domain.model

data class Movie(
    val genreIds: List<Int>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String
)
