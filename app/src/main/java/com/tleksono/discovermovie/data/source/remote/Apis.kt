package com.tleksono.discovermovie.data.source.remote

import com.tleksono.discovermovie.data.entity.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Apis {
    // remove suspend when using call<T>
    @GET("genre/movie/list")
    fun getMovieGenre(): Call<GenreEntity>

    @GET("discover/movie?language=en-US&include_video=false")
    fun getDiscoverMovie(
        @Query("page") page: Int,
        @Query("with_genres") genres: String
    ): Call<PageEntity<MovieEntity>>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Long
    ): Call<MovieDetailEntity>

    @GET("movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") movieId: Long
    ): Call<PageEntity<MovieReviewEntity>>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Long
    ): Call<MovieTrailerEntity>
}
