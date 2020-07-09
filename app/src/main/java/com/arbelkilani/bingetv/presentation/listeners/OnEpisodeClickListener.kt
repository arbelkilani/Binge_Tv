package com.arbelkilani.bingetv.presentation.listeners

import android.view.View
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity

interface OnEpisodeClickListener {
    fun onEpisodeClicked(episodeData: EpisodeData)

    fun onWatchedEpisodeClicked(view: View, episodeEntity: EpisodeEntity)
}