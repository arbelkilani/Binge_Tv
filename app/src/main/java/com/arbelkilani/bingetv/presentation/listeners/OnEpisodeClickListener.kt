package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.episode.Episode

interface OnEpisodeClickListener {
    fun onEpisodeClicked(episode: Episode)
}