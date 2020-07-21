package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemRecommendationBindingImpl
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class WatchedAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : RecyclerView.Adapter<WatchedAdapter.WatchedHolder>() {

    private var tvShows = mutableListOf<TvShowEntity>()

    class WatchedHolder(val itemRecommendationBinding: ItemRecommendationBindingImpl) :
        RecyclerView.ViewHolder(itemRecommendationBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedHolder {
        val itemRecommendationBindingImpl = DataBindingUtil.inflate<ItemRecommendationBindingImpl>(
            LayoutInflater.from(parent.context), R.layout.item_recommendation, parent, false
        )
        return WatchedHolder(itemRecommendationBindingImpl)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: WatchedHolder, position: Int) {
        holder.itemRecommendationBinding.tvShowEntity = tvShows[position]
        holder.itemRecommendationBinding.tvShowListener = onTvShowClickListener
    }

    fun notifyDataSetChanged(tvShows: List<TvShowEntity>) {
        this.tvShows = tvShows.toMutableList()
        notifyDataSetChanged()
    }
}