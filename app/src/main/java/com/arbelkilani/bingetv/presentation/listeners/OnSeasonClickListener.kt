package com.arbelkilani.bingetv.presentation.listeners

import android.view.View
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

interface OnSeasonClickListener {
    fun onSeasonItemClicked(seasonEntity: SeasonEntity)

    fun onWatchedSeasonClicked(view: View, seasonEntity: SeasonEntity)
}