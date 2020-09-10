package com.tleksono.discovermovie.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tleksono.discovermovie.data.source.MoviePagingSource
import com.tleksono.discovermovie.data.source.repo.AppRepository
import com.tleksono.discovermovie.domain.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieUsecase @Inject constructor(private val appRepository: AppRepository) {

    fun getMovie(genreIds: Int): Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = 20),
            pagingSourceFactory = { MoviePagingSource(appRepository, genreIds.toString()) })
            .flow
    }

    suspend fun getMovieDetail(movieId: Long): State<MovieDetail?> {
        return withContext(Dispatchers.IO) { appRepository.getMovieDetail(movieId) }
    }

    suspend fun getMovieTrailer(movieId: Long): State<List<MovieTrailer>> {
        return withContext(Dispatchers.IO) { appRepository.getMovieVideos(movieId) }
    }

    suspend fun getMovieReview(movieId: Long): State<List<MovieReview>> {
        return withContext(Dispatchers.IO) { appRepository.getMovieReviews(movieId) }
    }

}
