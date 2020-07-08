package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.entities.episode.EpisodeData

interface OnEpisodeClickListener {
    fun onEpisodeClicked(episodeData: EpisodeData)
}