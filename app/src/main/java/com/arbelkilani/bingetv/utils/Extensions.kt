package com.arbelkilani.bingetv.utils

import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@BindingAdapter("android:src")
fun bindImageUrl(view: ImageView, url: String?) {
    url?.let {
        Picasso.get().load(url).into(view)
    }
}

fun spannableVoteRate(v: String): String {
    val on = "/10"
    val value = String.format("%s%s", v, on, Locale.getDefault())
    val spannableString = SpannableString(value)
    spannableString.setSpan(
        RelativeSizeSpan(1.8f),
        value.length - on.length,
        value.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString.toString()
}

fun convertDpToPixel(dp: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else {
        val metrics = Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

fun convertPixelsToDp(px: Float, context: Context?): Float {
    return if (context != null) {
        val resources = context.resources
        val metrics = resources.displayMetrics
        px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else {
        val metrics = Resources.getSystem().displayMetrics
        px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}

fun returnDuration(dateToValue: String): String {

    val dateToFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateTo: Date = dateToFormat.parse(dateToValue)!!
    val currentDate: Date = Date(System.currentTimeMillis())

    //INFO : we add + 1 because the returned date from the api does not consider time it starts from 00:00:00
    val duration =
        TimeUnit.DAYS.convert((dateTo.time - currentDate.time), TimeUnit.MILLISECONDS) + 1
    return duration.toString()
}