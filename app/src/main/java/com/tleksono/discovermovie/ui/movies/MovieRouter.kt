package com.tleksono.discovermovie.ui.movies

import com.tleksono.discovermovie.common.Screen
import com.tleksono.discovermovie.ui.moviedetail.MovieDetailRouter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MovieRouter @Inject constructor(val router: Router) {

    fun openMovieDetail(movieId: Long) {
        router.navigateTo(Screen.FragmentScreen(MovieDetailRouter.movieDetailFragment(movieId)))
    }
}
