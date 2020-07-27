package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemTvShowSearchBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class SearchAdapter(private val tvShowClickListener: OnTvShowClickListener) :
    PagingDataAdapter<TvShowEntity, RecyclerView.ViewHolder>(TvShowComparator) {

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
            (holder as SearchHolder).itemTvShowSearchBinding.tvShowEntity = tvShow
            holder.itemTvShowSearchBinding.onTvShowClickListener = tvShowClickListener
            holder.itemTvShowSearchBinding.position = position
        }
    }

    fun notifyTvShow(position: Int, tvShow: TvShowEntity) {
        if (position == -1)
            return

        getItem(position)?.let {
            it.watched = tvShow.watched
            it.watchlist = tvShow.watchlist
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