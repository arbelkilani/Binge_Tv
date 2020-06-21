package com.arbelkilani.bingetv.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.BindingAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.tv.Network
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_bottom_sheet_seasons.view.*
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

@BindingAdapter("genres")
fun setGenres(view: FlexboxLayout, list: List<Genre>?) {
    if (list == null)
        return

    view.removeAllViews()
    for (item in list) {
        val textView = TextView(ContextThemeWrapper(view.context, R.style.TextView_Genre), null, 0)
        textView.text = item.name
        view.addView(textView)
    }

    view.requestLayout()
    view.invalidate()
}

@BindingAdapter("networks")
fun setNetworks(view: FlexboxLayout, list: List<Network>?) {
    if (list == null)
        return

    view.removeAllViews()
    for (item in list) {
        val textView =
            TextView(ContextThemeWrapper(view.context, R.style.TextView_Network), null, 0)
        textView.text = item.name
        view.addView(textView)
    }

    view.requestLayout()
    view.invalidate()
}

/**
 * it : bottom sheet
 */
fun doOnBottomSheetDetailsSeason(it: View) {

    val behavior = BottomSheetBehavior.from(it)
    val maxTransitionX: Float = (it.width - it.seasons_sheet_collapsed.width).toFloat()

    when (behavior.state) {
        BottomSheetBehavior.STATE_EXPANDED -> {
            it.translationX = 0f
            it.seasons_sheet_collapsed.alpha = 0f
            it.seasons_sheet_expanded.alpha = 1f
        }
        BottomSheetBehavior.STATE_COLLAPSED -> {
            ObjectAnimator.ofFloat(it, "translationX", it.width.toFloat(), maxTransitionX)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    start()
                }
            it.seasons_sheet_collapsed.alpha = 1f
            it.seasons_sheet_expanded.alpha = 0f
        }
    }

    behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val inverseOffset = 1.0f - slideOffset
            it.translationX = maxTransitionX * inverseOffset
            it.seasons_sheet_collapsed.alpha = inverseOffset
            it.seasons_sheet_expanded.alpha = slideOffset
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
        }
    })

    it.seasons_sheet_collapsed.setOnClickListener {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}