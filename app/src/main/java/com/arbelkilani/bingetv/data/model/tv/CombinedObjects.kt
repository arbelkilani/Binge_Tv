package com.arbelkilani.bingetv.data.model.tv

import android.os.Parcelable
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CombinedObjects(val airing: ApiResponse<Tv>, val trending: ApiResponse<Tv>) : Parcelable
