package com.arbelkilani.bingetv.presentation.ui.view

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.marginStart
import androidx.viewpager.widget.ViewPager
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.utils.dp2px
import kotlinx.android.synthetic.main.item_dicover_view.view.*
import kotlin.math.abs
import kotlin.math.roundToInt


class CustomTransformer : ViewPager.PageTransformer {

    private val TAG = CustomTransformer::class.java.simpleName

    companion object {
        const val DEFAULT_ELEVATION_VALUE = 0f
        const val DEFAULT_SCALE_VALUE = 1f
        const val DEFAULT_ALPHA_VALUE = 1f
    }

    private lateinit var viewPager: ViewPager

    /*override fun transformPage(page: View, position: Float) {
        val scaleFactor = 1 - abs(position)
        Log.i(TAG, "scaleFactor = $scaleFactor")

        /*when {
            position <= 0f -> {
                //page.scaleX = scaleFactor
                //page.scaleY = scaleFactor
                page.translationX = page.width * -position
                //page.elevation = scaleFactor
            }
            position <= 1f -> {
                page.translationX = DEFAULT_VALUE
                //page.scaleX = DEFAULT_VALUE
                //page.scaleY = DEFAULT_VALUE
                //page.elevation = DEFAULT_VALUE
            }
            position > 1 -> {
                //page.scaleX = scaleFactor
                //page.scaleY = scaleFactor
                page.translationX = page.width * +position
                //page.elevation = scaleFactor
            }
        }*/
    } */


    override fun transformPage(view: View, position: Float) {

        viewPager = view.parent as ViewPager

        val leftInScreen: Int = view.left - viewPager.scrollX
        val centerXInViewPager: Int = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - viewPager.measuredWidth / 2
        val offsetRate = offsetX.toFloat() * 0.4f / viewPager.measuredWidth
        val scaleFactor = 1 - abs(offsetRate)
        val alphaFactor = 1 - 2 * abs(offsetRate)
        view.alpha = DEFAULT_ALPHA_VALUE

        val titleView = viewPager.findViewById<TextView>(R.id.tv_title)
        titleView.alpha = DEFAULT_ALPHA_VALUE

        val translationFactor =
            (2 * view.width.toFloat() + 4 * (view.paddingStart + viewPager.pageMargin)) * -offsetRate

        if (scaleFactor > 0) {
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.translationX = translationFactor
            view.alpha = alphaFactor
            view.elevation = DEFAULT_ELEVATION_VALUE
            titleView.alpha = alphaFactor
        }

        view.elevation = scaleFactor
        titleView.alpha = DEFAULT_ALPHA_VALUE

    }
}