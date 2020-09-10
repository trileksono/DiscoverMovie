package com.tleksono.discovermovie.domain.model

data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean
)
