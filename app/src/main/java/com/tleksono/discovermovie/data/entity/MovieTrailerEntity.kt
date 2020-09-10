package com.tleksono.discovermovie.data.entity

import com.google.gson.annotations.SerializedName
import com.tleksono.discovermovie.domain.model.MovieTrailer

data class MovieTrailerEntity(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("results") val results: List<Result>? = null
) {
    data class Result(
        @SerializedName("id") val id: String? = null,
        @SerializedName("key") val key: String? = null,
        @SerializedName("name") val name: String? = null,
        @SerializedName("site") val site: String? = null,
        @SerializedName("size") val size: Int? = null,
        @SerializedName("type") val type: String? = null
    ) {
        fun toMovieTrailer(): MovieTrailer {
            return MovieTrailer(
                id.orEmpty(),
                key.orEmpty(),
                name.orEmpty(),
                site.orEmpty(),
                size ?: 0,
                type.orEmpty()
            )
        }
    }
}
