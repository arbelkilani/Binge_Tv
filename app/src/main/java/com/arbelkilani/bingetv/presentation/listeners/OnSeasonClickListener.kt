package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.season.Season

interface OnSeasonClickListener {
    fun onSeasonItemClicked(season: Season)
}