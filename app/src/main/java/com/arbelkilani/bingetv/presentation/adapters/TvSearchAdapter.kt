package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ItemTvShowSearchBinding

class TvSearchAdapter(private val tvShows: List<Tv>) :
    RecyclerView.Adapter<TvSearchAdapter.TvSearchHolder>() {

    class TvSearchHolder(val itemTvShowSearchBinding: ItemTvShowSearchBinding) :
        RecyclerView.ViewHolder(itemTvShowSearchBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvSearchHolder {
        val itemTvShowSearchBinding = DataBindingUtil.inflate<ItemTvShowSearchBinding>(
            LayoutInflater.from(parent.context), R.layout.item_tv_show_search, parent, false
        )
        return TvSearchHolder(itemTvShowSearchBinding)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: TvSearchHolder, position: Int) {
        holder.itemTvShowSearchBinding.tv = tvShows[position]
    }
}