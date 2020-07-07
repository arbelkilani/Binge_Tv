package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemSeasonLargeBindingImpl
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener

class SeasonAdapter(
    private val seasonClickListener: OnSeasonClickListener
) :
    RecyclerView.Adapter<SeasonAdapter.SeasonHolder>() {

    private var seasons = listOf<SeasonEntity>()

    class SeasonHolder(val itemSeasonsBinding: ItemSeasonLargeBindingImpl) :
        RecyclerView.ViewHolder(itemSeasonsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonHolder {
        val itemSeasonBindingImpl = DataBindingUtil.inflate<ItemSeasonLargeBindingImpl>(
            LayoutInflater.from(parent.context), R.layout.item_season_large, parent, false
        )
        return SeasonHolder(itemSeasonBindingImpl)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun onBindViewHolder(holder: SeasonHolder, position: Int) {
        holder.itemSeasonsBinding.seasonEntity = seasons[position]
        holder.itemSeasonsBinding.seasonClickListener = seasonClickListener
    }

    fun notifyDataSetChanged(seasons: List<SeasonEntity>) {
        this.seasons = seasons
        notifyDataSetChanged()
    }

    companion object {
        private const val TAG = "SeasonAdapter"
    }
}