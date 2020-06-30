package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ItemTvShowSearchBinding
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class SearchAdapter(private val tvShowClickListener: OnTvShowClickListener) :
    PagingDataAdapter<Tv, RecyclerView.ViewHolder>(TvShowComparator) {

    class SearchHolder(val itemTvShowSearchBinding: ItemTvShowSearchBinding) :
        RecyclerView.ViewHolder(itemTvShowSearchBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val itemTvShowSearchBinding = DataBindingUtil.inflate<ItemTvShowSearchBinding>(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_search, parent, false
        )
        return SearchHolder(itemTvShowSearchBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            (holder as SearchHolder).itemTvShowSearchBinding.tv = tvShow
            holder.itemTvShowSearchBinding.itemClick = tvShowClickListener
        }
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