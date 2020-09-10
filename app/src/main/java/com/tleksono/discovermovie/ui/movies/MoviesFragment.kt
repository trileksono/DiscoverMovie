package com.tleksono.discovermovie.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.tleksono.discovermovie.R
import com.tleksono.discovermovie.common.ext.parseError
import com.tleksono.discovermovie.domain.model.Genre
import com.tleksono.discovermovie.domain.model.State
import com.tleksono.discovermovie.ui.mainactivity.MainActivity
import com.tleksono.discovermovie.ui.movies.adapter.MoviesAdapter
import com.tleksono.discovermovie.ui.movies.adapter.MoviesStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    @Inject
    lateinit var movieRouter: MovieRouter
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var movieAdapter: MoviesAdapter
    private var genreId = 0
    private lateinit var rootView: View

    companion object {
        const val STATE_GENRE_MOVIE = "movie_genre_state"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        rootView = (activity as AppCompatActivity).findViewById<View>(android.R.id.content).rootView
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            genreId = savedInstanceState.getInt(STATE_GENRE_MOVIE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.app_name)
        movieAdapter = MoviesAdapter {
            movieRouter.openMovieDetail(it.id.toLong())
        }
        recycleMovie.run {
            val gridLayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
            gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (movieAdapter.getItemViewType(position)) {
                        MoviesAdapter.LOADING_VIEW_TYPE -> 1
                        else -> 2
                    }
                }
            }
            layoutManager = gridLayout
            adapter = movieAdapter.withLoadStateFooter(
                footer = MoviesStateAdapter {
                    if (movieAdapter.itemCount == 0) {
                        viewModel.onGetDataMovie(genreId)
                    } else {
                        movieAdapter.retry()
                    }
                }
            )

            movieAdapter.addLoadStateListener { state ->
                // Only show the list if refresh succeeds.
                recycleMovie.isVisible = state.source.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = state.source.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retry_button.isVisible = state.source.refresh is LoadState.Error

                val errorState = state.source.append as? LoadState.Error
                    ?: state.source.prepend as? LoadState.Error
                    ?: state.append as? LoadState.Error
                    ?: state.prepend as? LoadState.Error

                errorState?.let {
                    showToast(it.error.parseError())
                }
            }
        }

        retry_button.setOnClickListener {
            viewModel.onGetGenre()
        }

        observeViewModel()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_GENRE_MOVIE, genreId)
        super.onSaveInstanceState(outState)
    }


    private fun observeViewModel() {
        viewModel.run {
            loading.observe(viewLifecycleOwner, Observer {
                progressBar.isVisible = it
            })

            state.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    State.Status.SUCCESS -> bindGenreChip(it.data!!)
                    State.Status.ERROR -> {
                        showToast(it.message.orEmpty())
                        retry_button.isVisible = true
                    }
                }
            })

            movies.observe(viewLifecycleOwner, Observer {
                movieAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun bindGenreChip(genres: List<Genre>) {
        genres.forEachIndexed { index, it ->
            val chip = layoutInflater.inflate(R.layout.chip_genre, genreChips, false) as Chip
            chip.id = it.id
            chip.text = it.name
            chip.isClickable = true
            chip.isCheckable = true
            genreChips.addView(chip)
            chip.setOnClickListener {
                if (genreId == it.id) {
                    genreChips.findViewById<Chip>(it.id).isChecked = true
                    return@setOnClickListener
                }
                genreId = it.id
                (recycleMovie.layoutManager as GridLayoutManager).scrollToPosition(0)
                viewModel.onGetDataMovie(it.id)
            }
            if (genreId == 0 && index == 0) {
                chip.isChecked = true
            } else if (genreId == it.id)
                chip.isChecked = true
        }
    }

    private fun showToast(text: String) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show()
    }
}
