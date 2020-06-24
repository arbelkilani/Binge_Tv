package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.tv.Tv

interface OnTvShowClickListener {
    fun onTvItemClicked(tv: Tv)
}