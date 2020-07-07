package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity

interface OnSeasonClickListener {
    fun onSeasonItemClicked(seasonEntity: SeasonEntity)
}