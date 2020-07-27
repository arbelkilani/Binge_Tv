package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemTrendingViewBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener


class OnTheAirAdapter(private val tvShowClickListener: OnTvShowClickListener) :
    PagingDataAdapter<TvShowEntity, RecyclerView.ViewHolder>(TvShowComparator) {

    class OnTheAirHolder(val itemTrendingViewBinding: ItemTrendingViewBinding) :
        RecyclerView.ViewHolder(itemTrendingViewBinding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnTheAirHolder {

        val itemTrendingViewBinding = DataBindingUtil.inflate<ItemTrendingViewBinding>(
            LayoutInflater.from(parent.context), R.layout.item_trending_view, parent, false
        )
        return OnTheAirHolder(itemTrendingViewBinding)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val tvShow = getItem(position)
        if (tvShow != null) {
            (holder as OnTheAirHolder).itemTrendingViewBinding.tvEntity = tvShow
            holder.itemTrendingViewBinding.itemClick = tvShowClickListener
            holder.itemTrendingViewBinding.position = position
            holder.itemTrendingViewBinding.adapter = OnTheAirAdapter::class.java.simpleName
        }
    }

    fun notifyTvShow(position: Int, tvShow: TvShowEntity) {
        if (position == -1)
            return

        getItem(position)?.let {
            if (it.id == tvShow.id) {
                it.watched = tvShow.watched
                it.watchlist = tvShow.watchlist
            }
        }
        notifyItemChanged(position)
    }

    companion object {
        private val TvShowComparator = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


}