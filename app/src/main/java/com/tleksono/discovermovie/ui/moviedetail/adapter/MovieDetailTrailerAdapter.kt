package com.tleksono.discovermovie.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tleksono.discovermovie.R
import com.tleksono.discovermovie.common.ext.bindImage
import com.tleksono.discovermovie.common.ext.buildImageYoutubeUrl
import com.tleksono.discovermovie.domain.model.MovieTrailer
import kotlinx.android.synthetic.main.item_trailer.view.*

class MovieDetailTrailerAdapter(
    private val trailers: List<MovieTrailer>,
    val listener: (url: String) -> Unit
) : RecyclerView.Adapter<MovieDetailTrailerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trailer, parent, false)
        )
    }

    override fun getItemCount(): Int = trailers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.run {
            imgTrailer.bindImage(trailers[position].key.buildImageYoutubeUrl())
            setOnClickListener{
                listener(trailers[position].key)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}
