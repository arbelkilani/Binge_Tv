package com.arbelkilani.bingetv.presentation.listeners

import com.arbelkilani.bingetv.data.model.tv.Tv

interface OnTvClickListener {
    fun onTvItemClicked(tv : Tv)
}