package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending_view.view.*


class AiringTodayAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : PagingDataAdapter<Tv, RecyclerView.ViewHolder>(TvShowComparator) {

    class AiringTodayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            tv: Tv?,
            onTvShowClickListener: OnTvShowClickListener
        ) {
            tv?.apply {
                Picasso.get()
                    .load(posterPath)
                    .fit()
                    .centerCrop()
                    .into(itemView.iv_item_trending)
                itemView.tv_trending_name.text = name
                itemView.card_view.setOnClickListener {
                    onTvShowClickListener.onTvItemClicked(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AiringTodayHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trending_view, parent, false)
        return AiringTodayHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null)
            (holder as AiringTodayHolder).bind(tvShow, onTvShowClickListener)
    }

    companion object {
        private val TvShowComparator = object : DiffUtil.ItemCallback<Tv>() {
            override fun areItemsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tv, newItem: Tv): Boolean {
                return oldItem == newItem
            }
        }
    }
}