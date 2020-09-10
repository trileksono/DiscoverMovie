package com.tleksono.discovermovie.data.source.repo

import com.tleksono.discovermovie.common.NetworkHelper
import com.tleksono.discovermovie.common.NoInternetException
import com.tleksono.discovermovie.common.ext.parseError
import com.tleksono.discovermovie.common.ext.safeExecute
import com.tleksono.discovermovie.data.source.remote.Apis
import com.tleksono.discovermovie.domain.model.*
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apis: Apis,
    private val networkHelper: NetworkHelper
) : AppRepository {
    override suspend fun getGenres(): State<List<Genre>> {
        return when (networkHelper.isNetworkConnected()) {
            true -> apis.getMovieGenre().safeExecute { it.genreResponses.map { it.createGenre() } }
            false -> State.error(NoInternetException().parseError())
        }
    }

    override suspend fun getMovies(page: Int, genreIds: String): State<List<Movie>> {
        return when (networkHelper.isNetworkConnected()) {
            true -> apis.getDiscoverMovie(page, genreIds).safeExecute {
                it.results?.map { it.createMovie() } ?: emptyList()
            }
            false -> State.error(NoInternetException().parseError())
        }
    }

    override suspend fun getMovieDetail(movieId: Long): State<MovieDetail> {
        return when (networkHelper.isNetworkConnected()) {
            true -> apis.getMovieDetail(movieId).safeExecute { it.toMovieDetail() }
            false -> State.error(NoInternetException().parseError())
        }
    }

    override suspend fun getMovieReviews(movieId: Long): State<List<MovieReview>> {
        return when (networkHelper.isNetworkConnected()) {
            true -> apis.getMovieReviews(movieId)
                .safeExecute { it.results?.map { it.toMovieReview() } ?: listOf() }
            false -> State.error(NoInternetException().parseError())
        }
    }

    override suspend fun getMovieVideos(movieId: Long): State<List<MovieTrailer>> {
        return when (networkHelper.isNetworkConnected()) {
            true -> apis.getMovieVideos(movieId)
                .safeExecute { it.results?.map { it.toMovieTrailer() } ?: listOf() }
            false -> State.error(NoInternetException().parseError())
        }
    }
}
