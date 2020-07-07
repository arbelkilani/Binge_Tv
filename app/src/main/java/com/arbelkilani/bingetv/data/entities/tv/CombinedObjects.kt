package com.arbelkilani.bingetv.data.entities.tv

import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.entities.base.ApiResponse
import kotlinx.android.parcel.RawValue

data class CombinedObjects(
    val trending: ApiResponse<TvShowData>,
    val discover: @RawValue PagingData<TvShowData>
)