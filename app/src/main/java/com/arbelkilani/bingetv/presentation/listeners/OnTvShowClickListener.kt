package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.tv.TvShow

interface OnTvShowClickListener {
    fun onTvItemClicked(tv: TvShow)
}