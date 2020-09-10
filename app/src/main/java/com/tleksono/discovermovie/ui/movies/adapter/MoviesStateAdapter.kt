package com.tleksono.discovermovie.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tleksono.discovermovie.R
import kotlinx.android.synthetic.main.item_loading.view.*

class MoviesStateAdapter(val listener: () -> Unit) : LoadStateAdapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, loadState: LoadState) {
        holder.itemView.run {
            progress_bar.isVisible = loadState is LoadState.Loading
            retry_button.isVisible = loadState !is LoadState.Loading

            retry_button.setOnClickListener {
                listener()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecyclerView.ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
    )

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
