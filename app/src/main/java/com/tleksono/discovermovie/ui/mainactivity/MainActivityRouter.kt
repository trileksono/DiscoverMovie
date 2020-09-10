package com.tleksono.discovermovie.ui.mainactivity

import com.tleksono.discovermovie.common.Screen
import com.tleksono.discovermovie.ui.movies.MoviesFragment
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainActivityRouter @Inject constructor(val router: Router) {

    fun openFirstScreen() {
        router.replaceScreen(
            Screen.FragmentScreen(MoviesFragment())
        )
    }
}
