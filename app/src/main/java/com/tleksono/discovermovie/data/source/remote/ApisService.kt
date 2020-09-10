package com.tleksono.discovermovie.data.source.remote

import com.tleksono.discovermovie.data.entity.*
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class ApisService @Inject constructor(retrofit: Retrofit) : Apis {

    private val apis by lazy { retrofit.create(Apis::class.java) }

    override fun getMovieGenre(): Call<GenreEntity> {
        return apis.getMovieGenre()
    }

    override fun getDiscoverMovie(
        page: Int,
        genres: String
    ): Call<PageEntity<MovieEntity>> {
        return apis.getDiscoverMovie(page, genres)
    }

    override fun getMovieDetail(movieId: Long): Call<MovieDetailEntity> {
        return apis.getMovieDetail(movieId)
    }

    override fun getMovieReviews(movieId: Long): Call<PageEntity<MovieReviewEntity>> {
        return apis.getMovieReviews(movieId)
    }

    override fun getMovieVideos(movieId: Long): Call<MovieTrailerEntity> {
        return apis.getMovieVideos(movieId)
    }
}
