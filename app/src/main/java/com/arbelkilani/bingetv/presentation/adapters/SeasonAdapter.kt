package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.databinding.ItemSeasonBindingImpl
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_season.view.*

//TODO check how to bind data to recycler view using data binding jetpack
class SeasonAdapter(
    private val seasons: List<Season>,
    private val onSeasonClickListener: OnSeasonClickListener
) :
    RecyclerView.Adapter<SeasonAdapter.SeasonHolder>() {

    class SeasonHolder(val itemSeasonsBinding: ItemSeasonBindingImpl) :
        RecyclerView.ViewHolder(itemSeasonsBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonHolder {
        val itemSeasonBindingImpl = DataBindingUtil.inflate<ItemSeasonBindingImpl>(
            LayoutInflater.from(parent.context), R.layout.item_season, parent, false
        )
        return SeasonHolder(itemSeasonBindingImpl)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun onBindViewHolder(holder: SeasonHolder, position: Int) {
        holder.itemSeasonsBinding.season = seasons[position]
    }
}