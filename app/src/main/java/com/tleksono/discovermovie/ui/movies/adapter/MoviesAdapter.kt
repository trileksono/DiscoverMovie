package com.tleksono.discovermovie.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tleksono.discovermovie.R
import com.tleksono.discovermovie.common.ext.bindImagePalette
import com.tleksono.discovermovie.common.ext.buildPosterUrl
import com.tleksono.discovermovie.domain.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(val listener: (Movie) -> Unit) :
    PagingDataAdapter<Movie, RecyclerView.ViewHolder>(MovieComparator) {

    companion object {
        const val LOADING_VIEW_TYPE = 1
        const val CONTENT_VIEW_TYPE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.run {
            title.text = getItem(position)?.title
            title.isSelected = true

            imageView.bindImagePalette(getItem(position)?.posterPath?.buildPosterUrl(), title)

            setOnClickListener {
                getItem(position)?.let {
                    listener(it)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            CONTENT_VIEW_TYPE
        } else {
            LOADING_VIEW_TYPE
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    object MovieComparator : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}
