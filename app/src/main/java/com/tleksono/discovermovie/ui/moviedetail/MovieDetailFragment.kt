package com.tleksono.discovermovie.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tleksono.discovermovie.R
import com.tleksono.discovermovie.common.ext.bindImage
import com.tleksono.discovermovie.common.ext.buildBackdropUrl
import com.tleksono.discovermovie.common.ext.getYearReleaseDate
import com.tleksono.discovermovie.domain.model.MovieDetail
import com.tleksono.discovermovie.domain.model.MovieReview
import com.tleksono.discovermovie.domain.model.State
import com.tleksono.discovermovie.ui.moviedetail.MovieDetailRouter.Companion.ARGS_MOVIE_DETAIL_ID
import com.tleksono.discovermovie.ui.moviedetail.adapter.MovieDetailAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.toolbar_detail.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var movieDetailRouter: MovieDetailRouter
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieDetailAdapter: MovieDetailAdapter
    private lateinit var rootView: View
    private var isSnackbarShow = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        rootView = (activity as AppCompatActivity).findViewById<View>(android.R.id.content).rootView
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarParams = toolbarDetail.layoutParams as CollapsingToolbarLayout.LayoutParams
        toolbarParams.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
        toolbarDetail.layoutParams = toolbarParams
        appbarlayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            appbarlayout.post {
                try {
                    if (Math.abs(verticalOffset) >= appbarlayout.totalScrollRange) {
                        tvToolbarTitle.text = tvTitle.text.toString()
                    } else {
                        tvToolbarTitle.text = " "
                    }
                } catch (ex: IllegalStateException) {
                    println(ex.localizedMessage)
                }
            }
        })

        recyclerReview.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            movieDetailAdapter = MovieDetailAdapter(mutableListOf()) { youtubeUrl ->
                movieDetailRouter.openYoutubeVideo(youtubeUrl)
            }
            adapter = movieDetailAdapter
        }

        if (savedInstanceState == null) getData()

        imgBack.setOnClickListener { (activity as AppCompatActivity).onBackPressed() }
        btnTryAgain.setOnClickListener { getData() }

        observeViewModel()
    }

    private fun getData() {
        viewModel.initialMovieId(arguments?.getLong(ARGS_MOVIE_DETAIL_ID) ?: -1L)
    }

    private fun observeViewModel() {
        viewModel.run {
            movieDetailLiveData.observe(viewLifecycleOwner, Observer { it ->
                when (it.status) {
                    State.Status.SUCCESS -> {
                        bindDetail(it.data)
                        movieDetailAdapter.addView("Overview", 0)
                        it.data?.let { movieDetailAdapter.addView(it.overview, 1) }

                        btnTryAgain.isVisible = false
                        appbarlayout.visibility = View.VISIBLE
                    }
                    State.Status.ERROR -> handlingError(it.message)
                }
            })

            movieTrailerLiveData.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    State.Status.SUCCESS -> {
                        movieDetailAdapter.addView("Trailer", 2)
                        it.data?.let { movieDetailAdapter.addView(it, 3) }
                    }
                    State.Status.ERROR -> handlingError(it.message)
                }
            })

            movieReviewLiveData.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    State.Status.SUCCESS -> {
                        movieDetailAdapter.addView("Review", 4)
                        if (it.data.isNullOrEmpty()) {
                            movieDetailAdapter.addView(MovieReview("", ""), 5)
                        } else {
                            movieDetailAdapter.addReview(it.data, 5)
                        }
                    }
                    State.Status.ERROR -> handlingError(it.message)
                }
            })
        }
    }

    private fun bindDetail(movieDetail: MovieDetail?) {
        movieDetail?.let {
            tvTitle.text = "${it.title} (${movieDetail.releaseDate.getYearReleaseDate()})"
            imgBackdrop.bindImage(it.backdropPath.buildBackdropUrl())
        }
    }

    private fun handlingError(text: String?) {
        if (isSnackbarShow) return
        text?.let {
            isSnackbarShow = true
            Snackbar.make(rootView, it, Snackbar.LENGTH_SHORT)
                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar?>() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        if (event == DISMISS_EVENT_TIMEOUT) {
                            isSnackbarShow = false
                        }
                    }
                })
                .show()
        }
        btnTryAgain.isVisible = true
        appbarlayout.visibility = View.INVISIBLE
    }

}
