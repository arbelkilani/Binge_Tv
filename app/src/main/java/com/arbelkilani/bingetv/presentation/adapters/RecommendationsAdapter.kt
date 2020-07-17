package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemRecommendationBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class RecommendationsAdapter(private val tvShowClickListener: OnTvShowClickListener) :
    PagingDataAdapter<TvShowEntity, RecyclerView.ViewHolder>(TvShowComparator) {

    class RecommendationsHolder(val itemRecommendationBinding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(itemRecommendationBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemRecommendationBinding = DataBindingUtil.inflate<ItemRecommendationBinding>(
            LayoutInflater.from(parent.context), R.layout.item_recommendation, parent, false
        )
        return RecommendationsHolder(itemRecommendationBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            (holder as RecommendationsHolder).itemRecommendationBinding.tvShowEntity = tvShow
            holder.itemRecommendationBinding.tvShowListener = tvShowClickListener
        }
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