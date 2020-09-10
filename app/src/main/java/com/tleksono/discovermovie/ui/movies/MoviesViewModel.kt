package com.tleksono.discovermovie.ui.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tleksono.discovermovie.domain.model.Genre
import com.tleksono.discovermovie.domain.model.Movie
import com.tleksono.discovermovie.domain.model.State
import com.tleksono.discovermovie.domain.usecase.GenreUsecase
import com.tleksono.discovermovie.domain.usecase.MovieUsecase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ActivityRetainedScoped
class MoviesViewModel @ViewModelInject constructor(
    private val genreUsecase: GenreUsecase,
    private val movieUsecase: MovieUsecase
) : ViewModel(), LifecycleObserver {


    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = mLoading

    private val mState = MutableLiveData<State<List<Genre>>>()
    val state: LiveData<State<List<Genre>>>
        get() = mState

    private val mMovies = MutableLiveData<PagingData<Movie>>()
    val movies: LiveData<PagingData<Movie>>
        get() = mMovies

    init {
        mLoading.postValue(true)
        onGetGenre()
    }

    fun onGetGenre(){
        viewModelScope.launch {
            val stateGenre = genreUsecase.getGenre()
            if (stateGenre.status == State.Status.SUCCESS) {
                onGetDataMovie(stateGenre.data?.first()?.id!!.toInt())
            }
            mState.postValue(stateGenre)
            mLoading.postValue(false)
        }
    }

    fun onGetDataMovie(genreIds: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieUsecase.getMovie(genreIds)
                .cachedIn(this)
                .collect {
                    mMovies.postValue(it)
                }
        }
    }
}
