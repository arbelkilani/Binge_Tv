package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.tv.Tv

interface OnSeasonClickListener {
    fun onSeasonItemClicked(tv : Tv)
}