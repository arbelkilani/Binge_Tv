package com.arbelkilani.bingetv.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    //TODO work on error and placeholder
    Picasso.get().load(url).fit().centerCrop().into(view)
}

fun returnDuration(dateToValue: String): String {
    val year = dateToValue.substring(0, 4)
    val month = dateToValue.substring(5, 7)
    val day = dateToValue.substring(8, dateToValue.length)

    val dateToFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateTo: Date = dateToFormat.parse(dateToValue)!!
    val currentDate: Date = Date(System.currentTimeMillis())

    Log.i("TAG**", "dateTo = $dateTo")
    Log.i("TAG**", "currentDate = $currentDate")

    //INFO : we add + 1 because the returned date from the api does not consider time it starts from 00:00:00
    val duration =
        TimeUnit.DAYS.convert((dateTo.time - currentDate.time), TimeUnit.MILLISECONDS) + 1
    return duration.toString()
}