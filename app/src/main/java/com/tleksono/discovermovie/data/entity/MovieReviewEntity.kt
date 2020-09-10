package com.tleksono.discovermovie.data.entity

import com.google.gson.annotations.SerializedName
import com.tleksono.discovermovie.domain.model.MovieReview

data class MovieReviewEntity(
    @SerializedName("author") val author: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("url") val url: String?
) {
    fun toMovieReview(): MovieReview {
        return MovieReview(author.orEmpty(), content.orEmpty())
    }
}
