package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_season.view.*

//TODO check how to bind data to recycler view using data binding jetpack
class SeasonAdapter(
    private val seasons: List<Season>,
    private val onSeasonClickListener: OnSeasonClickListener
) :
    RecyclerView.Adapter<SeasonAdapter.SeasonHolder>() {

    class SeasonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(season: Season, onSeasonClickListener: OnSeasonClickListener) {
            Picasso.get()
                .load(season.posterPath)
                .fit().centerCrop()
                .into(itemView.iv_season_poster)
            itemView.tv_season_number.text = season.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_season, parent, false)
        return SeasonHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }

    override fun onBindViewHolder(holder: SeasonHolder, position: Int) {
        holder.bind(seasons[position], onSeasonClickListener)
    }
}