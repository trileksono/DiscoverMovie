package com.tleksono.discovermovie.data.entity

import com.google.gson.annotations.SerializedName
import com.tleksono.discovermovie.domain.model.Genre

data class GenreEntity(@SerializedName("genres") val genreResponses: List<GenreResponse>) {

    data class GenreResponse(
        @SerializedName("id") val id: Int?,
        @SerializedName("name") val name: String?
    ) {
        fun createGenre(): Genre {
            return Genre(
                id = id ?: 0,
                name = name.orEmpty()
            )
        }
    }
}
