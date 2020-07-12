package com.arbelkilani.bingetv.presentation.ui.view

import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.arbelkilani.bingetv.R
import kotlin.math.abs


class CarouselTransformer : ViewPager.PageTransformer {

    private val TAG = CarouselTransformer::class.java.simpleName

    companion object {
        const val TAG = "CarouselTransformer"
        const val DEFAULT_ELEVATION_VALUE = 0f
        const val DEFAULT_ALPHA_VALUE = 1f
    }

    private lateinit var viewPager: ViewPager

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