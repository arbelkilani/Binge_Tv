package com.arbelkilani.bingetv.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.GradientDrawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.entities.genre.Genre
import com.arbelkilani.bingetv.data.entities.tv.Network
import com.arbelkilani.bingetv.data.entities.tv.maze.details.NextEpisodeData
import com.arbelkilani.bingetv.presentation.listeners.KeyboardListener
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
        Picasso.get().load(url)
            .into(view)
    }
}

@BindingAdapter("android:isSelected")
fun setSelected(view: ImageView, selected: Boolean) {
    view.isSelected = selected
}

@BindingAdapter("android:progressAnimated")
fun animateProgress(view: ProgressBar, value: Int) {
    val animation = ObjectAnimator.ofInt(view, "progress", 0, value)
    animation.duration = 300
    animation.interpolator = AccelerateDecelerateInterpolator()
    animation.start()
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

private fun blendColors(from: Int, to: Int, ratio: Float): Int {
    val inverseRatio = 1f - ratio
    val r: Float = Color.red(to) * ratio + Color.red(from) * inverseRatio
    val g: Float = Color.green(to) * ratio + Color.green(from) * inverseRatio
    val b: Float = Color.blue(to) * ratio + Color.blue(from) * inverseRatio
    return Color.rgb(r.toInt(), g.toInt(), b.toInt())
}

fun doOnBottomSheetDetailsSeason(it: View) {

    val behavior = BottomSheetBehavior.from(it)
    val maxTransitionX: Float = (it.width - it.seasons_sheet_collapsed.width).toFloat()

    val drawable = it.background.mutate() as GradientDrawable

    val initialColor =
        ContextCompat.getColor(it.context, R.color.season_sheet_background_color_initial)
    val endColor = ContextCompat.getColor(it.context, R.color.season_sheet_background_color_end)
    val cornerRadius = it.resources.getDimension(R.dimen.details_bottom_sheet_corner_radius)

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

            drawable.setColor(blendColors(initialColor, endColor, slideOffset))
            drawable.cornerRadius = cornerRadius * inverseOffset

        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    it.seasons_sheet_collapsed.isClickable = false
                    it.seasons_sheet_collapsed.isFocusable = false
                }

                BottomSheetBehavior.STATE_COLLAPSED -> {
                    it.seasons_sheet_collapsed.isClickable = true
                    it.seasons_sheet_collapsed.isFocusable = true
                }
            }
        }
    })

    it.seasons_sheet_collapsed.setOnClickListener {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}

fun formatAirDate(data: NextEpisodeData?): String {

    if (data == null) return ""

    data.apply {

        if (data.airTime.isEmpty())
            data.airTime = "00:00"

        val hourOfDay = airTime.substringBefore(":").toInt()
        val minute = airTime.substringAfter(":").toInt()

        val year = airDate.substringBefore("-").toInt()
        val month = airDate.substringBeforeLast("-").substringAfter("-").toInt()
        val date = airDate.substringAfterLast("-").toInt()

        val calendar = Calendar.getInstance(TimeZone.getTimeZone(timezone))
        calendar.set(year, month - 1, date, hourOfDay, minute)

        val simpleDateFormat =
            SimpleDateFormat("EEEE, d MMMM yyyy 'at' HH:mm a", Locale.getDefault())

        return simpleDateFormat.format(calendar.timeInMillis)

    }
}

/**
 * Return type HashMap<Int, Int>
 *     hashMap[0] -> x axis
 *     hashMap[1] -> y axis
 */
fun getMenuItemAxis(item: MenuItem, context: Context?): HashMap<Int, Int> {

    val axis = hashMapOf<Int, Int>()
    context?.let {

        val contextWidth = context.resources.displayMetrics.widthPixels
        val contextHeight = context.resources.displayMetrics.heightPixels

        if (item.icon != null) {
            (item.icon.mutate() as BitmapDrawable).bitmap?.apply {
                axis[0] = (contextWidth - ((2 - item.order) * width)) - width / 2
                axis[1] = contextHeight - (contextHeight - height / 2)
            }
        } else {
            TextView(context).apply {
                text = item.title
                measure(0, 0)
                axis[0] = (contextWidth - ((2 - item.order) * measuredWidth)) - measuredWidth
                axis[1] = contextHeight - (contextHeight - measuredHeight / 2)
            }
        }
    }

    return axis
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.apply {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun AppCompatActivity.showKeyboard(view: View) {
    view.apply {
        requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}

fun AppCompatActivity.interceptKeyboardVisibility(keyboardListener: KeyboardListener) {
    val decorView = this.window.decorView
    decorView.viewTreeObserver.addOnGlobalLayoutListener(object :
        ViewTreeObserver.OnGlobalLayoutListener {

        val windowVisibleDisplayFrame = Rect()
        var lastVisibleDecorViewHeight: Int = 0

        override fun onGlobalLayout() {
            decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
            val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

            if (lastVisibleDecorViewHeight != 0) {
                if (lastVisibleDecorViewHeight > visibleDecorViewHeight + 150) {
                    val currentKeyboardHeight = decorView.height - windowVisibleDisplayFrame.bottom
                    keyboardListener.onKeyboardShown(currentKeyboardHeight)
                } else if (lastVisibleDecorViewHeight + 150 < visibleDecorViewHeight) {
                    keyboardListener.onKeyboardHidden()
                }
            }

            lastVisibleDecorViewHeight = visibleDecorViewHeight
        }
    })
}

fun setFadeAnimation(view: View) {
    view.apply {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 400
        startAnimation(anim)
    }
}