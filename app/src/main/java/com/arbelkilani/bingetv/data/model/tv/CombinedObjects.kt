package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import androidx.paging.PagingData
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CombinedObjects(val trending: ApiResponse<Tv>, val discover: @RawValue PagingData<Tv>) :
    Parcelable
