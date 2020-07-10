package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemEpisodeBinding
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.presentation.listeners.OnEpisodeClickListener

class EpisodeAdapter(
    private val episodeClickListener: OnEpisodeClickListener
) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeHolder>() {

    private var episodes = listOf<EpisodeEntity>()

    class EpisodeHolder(val itemEpisodeBinding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(itemEpisodeBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeHolder {
        val itemEpisodeBinding = DataBindingUtil.inflate<ItemEpisodeBinding>(
            LayoutInflater.from(parent.context), R.layout.item_episode, parent, false
        )
        return EpisodeHolder(itemEpisodeBinding)
    }

    override fun getItemCount(): Int {
        return episodes.size
    }

    override fun onBindViewHolder(holder: EpisodeHolder, position: Int) {
        holder.itemEpisodeBinding.episodeEntity = episodes[position]
        holder.itemEpisodeBinding.episodeClickListener = episodeClickListener
    }

    fun notifyDataSetChanged(episodes: List<EpisodeEntity>) {
        this.episodes = episodes
        notifyDataSetChanged()
    }

    fun notifyItemChanged(episodeEntity: EpisodeEntity) {
        val position = episodes.indexOf(episodeEntity)
        notifyItemChanged(position)
    }

    companion object {
        private const val TAG = "EpisodeAdapter"
    }
}