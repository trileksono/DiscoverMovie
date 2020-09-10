package com.tleksono.discovermovie.domain.model

data class MovieTrailer(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)
