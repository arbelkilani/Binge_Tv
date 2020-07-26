package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemTvShowReturningBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener


class WatchedAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : RecyclerView.Adapter<WatchedAdapter.WatchedHolder>() {

    private var tvShows = mutableListOf<TvShowEntity>()

    class WatchedHolder(val binding: ItemTvShowReturningBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedHolder {
        val binding = DataBindingUtil.inflate<ItemTvShowReturningBinding>(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_returning, parent, false
        )
        return WatchedHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: WatchedHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.binding.tvShowEntity = tvShows[position]
        holder.binding.tvShowListener = onTvShowClickListener
    }

    fun notifyDataSetChanged(tvShows: List<TvShowEntity>) {
        this.tvShows = tvShows.toMutableList()
        notifyDataSetChanged()
    }
}