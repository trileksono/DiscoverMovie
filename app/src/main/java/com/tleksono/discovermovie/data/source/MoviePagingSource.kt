package com.tleksono.discovermovie.data.source

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tleksono.discovermovie.data.source.repo.AppRepository
import com.tleksono.discovermovie.domain.model.Movie
import com.tleksono.discovermovie.domain.model.State

class MoviePagingSource(
    private val repository: AppRepository,
    private val query: String
) : PagingSource<Int, Movie>() {

    private val FIRST_PAGE = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageRequest = params.key ?: FIRST_PAGE
        return try {
            val response = repository.getMovies(pageRequest, query)
            when (response.status) {
                State.Status.SUCCESS -> LoadResult.Page(
                    data = response.data ?: emptyList(),
                    prevKey = if (pageRequest == FIRST_PAGE) null else pageRequest - 1,
                    nextKey = if (response.data.isNullOrEmpty()) null else pageRequest + 1
                )
                State.Status.ERROR -> LoadResult.Error(Throwable(response.message))
            }

        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)!!.id
        }
    }
}
