package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity

interface OnTvShowClickListener {
    fun onTvItemClicked(tvShowEntity: TvShowEntity, position: Int)
}