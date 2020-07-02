package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.TvShow
import com.arbelkilani.bingetv.databinding.ItemTrendingViewBinding
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener


class OnTheAirAdapter(private val tvShowClickListener: OnTvShowClickListener) :
    PagingDataAdapter<TvShow, RecyclerView.ViewHolder>(TvShowComparator) {

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
            (holder as OnTheAirHolder).itemTrendingViewBinding.tv = tvShow
            holder.itemTrendingViewBinding.itemClick = tvShowClickListener
        }
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