package com.tleksono.discovermovie.ui.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.tleksono.discovermovie.common.Screen
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MovieDetailRouter @Inject constructor(val router: Router) {

    companion object {
        const val ARGS_MOVIE_DETAIL_ID = "argsMovieDetailMovieId"

        fun movieDetailFragment(movieId: Long): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()
            args.putLong(ARGS_MOVIE_DETAIL_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }


    fun openYoutubeVideo(youtubeUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://$youtubeUrl"))
        intent.putExtra("force_fullscreen", true)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        router.navigateTo(
            Screen.ActivityScreen(intent)
        )
    }
}
