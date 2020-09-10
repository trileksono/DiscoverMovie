package com.tleksono.discovermovie.data.entity

import com.google.gson.annotations.SerializedName
import com.tleksono.discovermovie.domain.model.Genre
import com.tleksono.discovermovie.domain.model.MovieDetail

data class MovieDetailEntity(
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("genres") val genres: List<GenreEntity.GenreResponse>? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("original_title") val originalTitle: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("popularity") val popularity: Double? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("video") val video: Boolean? = null
) {
    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            backdropPath.orEmpty(),
            genres?.map { Genre(it.id ?: 0, it.name.orEmpty()) } ?: emptyList(),
            id ?: 0,
            originalTitle.orEmpty(),
            overview.orEmpty(),
            popularity ?: 0.0,
            posterPath.orEmpty(),
            releaseDate.orEmpty(),
            title.orEmpty(),
            video ?: false
        )
    }
}
