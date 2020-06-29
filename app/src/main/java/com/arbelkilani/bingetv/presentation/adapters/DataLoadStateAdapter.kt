package com.arbelkilani.bingetv.presentation.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class DataLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<DataLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: DataLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): DataLoadStateViewHolder {
        return DataLoadStateViewHolder.create(parent, retry)
    }
}