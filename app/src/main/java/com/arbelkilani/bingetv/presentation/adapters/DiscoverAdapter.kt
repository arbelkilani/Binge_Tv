package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending_view.view.*

class DiscoverAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : PagingDataAdapter<TvShow, RecyclerView.ViewHolder>(TvShowComparator) {

    class DiscoverHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            tv: TvShow?,
            onTvShowClickListener: OnTvShowClickListener
        ) {
            tv?.apply {
                Picasso.get()
                    .load(posterPath)
                    .fit()
                    .centerCrop()
                    .into(itemView.iv_item_trending)
                itemView.setOnClickListener {
                    onTvShowClickListener.onTvItemClicked(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trending_view, parent, false)
        return DiscoverHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null)
            (holder as DiscoverHolder).bind(tvShow, onTvShowClickListener)
    }

    companion object {
        private val TvShowComparator = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }
        }
    }
}