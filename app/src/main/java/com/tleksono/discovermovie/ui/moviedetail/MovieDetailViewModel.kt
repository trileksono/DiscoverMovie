package com.tleksono.discovermovie.ui.moviedetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.tleksono.discovermovie.domain.model.MovieDetail
import com.tleksono.discovermovie.domain.model.MovieReview
import com.tleksono.discovermovie.domain.model.MovieTrailer
import com.tleksono.discovermovie.domain.model.State
import com.tleksono.discovermovie.domain.usecase.MovieUsecase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class MovieDetailViewModel @ViewModelInject constructor(
    private val movieUsecase: MovieUsecase
) : ViewModel(), LifecycleObserver {

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = mLoading

    var movieDetailLiveData = MutableLiveData<State<MovieDetail?>>()
    var movieTrailerLiveData = MutableLiveData<State<List<MovieTrailer>>>()
    var movieReviewLiveData = MutableLiveData<State<List<MovieReview>>>()

    fun initialMovieId(movieId: Long) {
        mLoading.postValue(true)
        viewModelScope.launch {
            val movieDetail = async { movieUsecase.getMovieDetail(movieId) }
            val movieTrailer = async { movieUsecase.getMovieTrailer(movieId) }
            val movieReview = async { movieUsecase.getMovieReview(movieId) }

            movieDetailLiveData.value = movieDetail.await()
            movieTrailerLiveData.value = movieTrailer.await()
            movieReviewLiveData.value = movieReview.await()
        }
    }
}
