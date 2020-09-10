package com.tleksono.discovermovie.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tleksono.discovermovie.R
import com.tleksono.discovermovie.domain.model.MovieReview
import com.tleksono.discovermovie.domain.model.MovieTrailer
import kotlinx.android.synthetic.main.item_list_trailer.view.*
import kotlinx.android.synthetic.main.item_overview.view.*
import kotlinx.android.synthetic.main.item_review.view.*
import kotlinx.android.synthetic.main.item_title.view.*

class MovieDetailAdapter(
    val data: MutableList<Any>,
    val listener: (youtubeUrl: String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_VIEW_TYPE_TITLE = 1
        const val ITEM_VIEW_TYPE_OVERVIEW = 2
        const val ITEM_VIEW_TYPE_TRAILER = 3
        const val ITEM_VIEW_TYPE_REVIEW = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_TITLE -> TitleViewHolder.createHolder(parent)
            ITEM_VIEW_TYPE_OVERVIEW -> OverviewViewHolder.createHolder(parent)
            ITEM_VIEW_TYPE_TRAILER -> TrailerViewHolder.createHolder(parent)
            else -> ReviewViewHolder.createHolder(parent)
        }
    }

    override fun getItemCount(): Int = data.size

    fun addView(data: Any, index: Int) {
        this.data.add(index, data)
        notifyItemChanged(index)
    }

    fun addReview(data: List<Any>?, index: Int) {
        data?.let {
            this.data.addAll(index, it)
            notifyItemRangeChanged(index, index + it.size)
        }
    }

    /**
     * For sample purpose
     *
     * This adapter using fixed index of view
     * 1. for every even number before 5 will be title view
     * 2. position 1 will be content overview
     * 3. position 3 will be content trailer
     * 4. for every position greater than 4 will be content review,
     *
     */

    override fun getItemViewType(position: Int): Int {
        return if ((position.rem(2) == 0 || position == 0) && position <= 4) {
            ITEM_VIEW_TYPE_TITLE
        } else if (position == 1) {
            ITEM_VIEW_TYPE_OVERVIEW
        } else if (position == 3) {
            ITEM_VIEW_TYPE_TRAILER
        } else {
            ITEM_VIEW_TYPE_REVIEW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ITEM_VIEW_TYPE_TITLE -> bindTitle(holder as TitleViewHolder, data[position] as String)
            ITEM_VIEW_TYPE_OVERVIEW -> bindOverview(
                holder as OverviewViewHolder,
                data[position] as String
            )
            ITEM_VIEW_TYPE_TRAILER -> bindTrailer(
                holder as TrailerViewHolder,
                data[position] as List<MovieTrailer>
            )
            ITEM_VIEW_TYPE_REVIEW -> bindReview(
                holder as ReviewViewHolder, data[position] as MovieReview
            )
        }
    }

    // region bind view
    private fun bindTitle(holder: TitleViewHolder, title: String) {
        holder.itemView.genreName.text = title
    }

    private fun bindOverview(holder: OverviewViewHolder, overview: String) {
        holder.itemView.run {
            if (overview.isEmpty()) {
                tvOverviewEmpty.isVisible = true
                tvOverview.isVisible = false
            } else {
                tvOverviewEmpty.isVisible = false
                tvOverview.isVisible = true
                tvOverview.text = overview
            }
        }
    }

    private fun bindTrailer(holder: TrailerViewHolder, trailers: List<MovieTrailer>) {
        val trailerYoutube = trailers.filter { it.site == "YouTube" && it.type == "Trailer" }
        holder.itemView.run {
            if (trailerYoutube.isEmpty()) {
                recycleTrailer.isVisible = false
                tvTrailerEmtpy.isVisible = true
            } else {
                recycleTrailer.isVisible = true
                tvTrailerEmtpy.isVisible = false
                recycleTrailer.run {
                    layoutManager = LinearLayoutManager(
                        holder.itemView.context, LinearLayoutManager.HORIZONTAL, false
                    )
                    adapter = MovieDetailTrailerAdapter(trailerYoutube) {
                        listener(it)
                    }
                }
            }
        }
    }

    private fun bindReview(holder: ReviewViewHolder, review: MovieReview) {
        holder.itemView.run {
            if (review.author.isEmpty() && review.content.isEmpty()) {
                tvEmptyReview.isVisible = true
            } else {
                tvEmptyReview.isVisible = false
                tvUser.text = "A review By ${review.author}"
                tvReview.text = review.content
            }
        }
    }
    // endregion

    // region viewholder

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun createHolder(parent: ViewGroup): TitleViewHolder {
                return TitleViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_title, parent, false)
                )
            }
        }
    }

    class OverviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun createHolder(parent: ViewGroup): OverviewViewHolder {
                return OverviewViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_overview, parent, false)
                )
            }
        }
    }

    class TrailerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun createHolder(parent: ViewGroup): TrailerViewHolder {
                return TrailerViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_list_trailer, parent, false)
                )
            }
        }
    }

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun createHolder(parent: ViewGroup): ReviewViewHolder {
                return ReviewViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_review, parent, false)
                )
            }
        }
    }
    // endregion
}
