package com.tleksono.discovermovie.data.source.repo

import com.tleksono.discovermovie.domain.model.*

interface AppRepository {

    suspend fun getGenres(): State<List<Genre>>

    suspend fun getMovies(page: Int, genreIds: String): State<List<Movie>>

    suspend fun getMovieDetail(movieId: Long): State<MovieDetail>

    suspend fun getMovieReviews(movieId: Long): State<List<MovieReview>>

    suspend fun getMovieVideos(movieId: Long): State<List<MovieTrailer>>
}
