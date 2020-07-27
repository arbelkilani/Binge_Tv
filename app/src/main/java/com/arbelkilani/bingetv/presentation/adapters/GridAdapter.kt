package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemTvShowSearchBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class GridAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : RecyclerView.Adapter<GridAdapter.GridHolder>() {

    private var tvShows = mutableListOf<TvShowEntity>()

    class GridHolder(val binding: ItemTvShowSearchBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridHolder {
        val binding = DataBindingUtil.inflate<ItemTvShowSearchBinding>(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_search, parent, false
        )
        return GridHolder(binding)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: GridHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.binding.tvShowEntity = tvShows[position]
        holder.binding.onTvShowClickListener = onTvShowClickListener
        holder.binding.position = position
        holder.binding.adapter = GridAdapter::class.java.simpleName
    }

    fun notifyDataSetChanged(tvShows: List<TvShowEntity>) {
        this.tvShows = tvShows.toMutableList()
        notifyDataSetChanged()
    }
}