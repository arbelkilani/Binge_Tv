package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.entities.episode.Episode

interface OnEpisodeClickListener {
    fun onEpisodeClicked(episode: Episode)
}