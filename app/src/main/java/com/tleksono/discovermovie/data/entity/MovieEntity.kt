package com.tleksono.discovermovie.data.entity

import com.google.gson.annotations.SerializedName
import com.tleksono.discovermovie.domain.model.Movie

data class MovieEntity(
    @SerializedName("genre_ids") val genreIds: List<Int>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null
) {
    fun createMovie(): Movie {
        return Movie(
            genreIds ?: listOf(),
            id ?: 0,
            originalTitle.orEmpty(),
            overview.orEmpty(),
            popularity ?: 0.0,
            posterPath.orEmpty(),
            releaseDate.orEmpty(),
            title.orEmpty()
        )
    }
}
