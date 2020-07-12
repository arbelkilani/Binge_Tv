package com.arbelkilani.bingetv.presentation.adapters.viewpager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemWatchlistBindingImpl
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener


class WatchlistAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : RecyclerView.Adapter<WatchlistAdapter.WatchlistHolder>() {

    private var tvShowList = listOf<TvShowEntity>()

    companion object {
        private const val TAG = "WatchlistAdapter"
    }

    class WatchlistHolder(val itemWatchlistBindingImpl: ItemWatchlistBindingImpl) :
        RecyclerView.ViewHolder(itemWatchlistBindingImpl.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistHolder {
        val itemWatchlistBindingImpl = DataBindingUtil.inflate<ItemWatchlistBindingImpl>(
            LayoutInflater.from(parent.context), R.layout.item_watchlist, parent, false
        )
        return WatchlistHolder(itemWatchlistBindingImpl)
    }

    override fun getItemCount(): Int {
        return tvShowList.size
    }

    override fun onBindViewHolder(holder: WatchlistHolder, position: Int) {
        holder.itemWatchlistBindingImpl.tvShowEntity = tvShowList[position]
        holder.itemWatchlistBindingImpl.tvShowListener = onTvShowClickListener
    }

    fun notifyDataSetChanged(it: List<TvShowEntity>) {
        tvShowList = it
        notifyDataSetChanged()
    }


}