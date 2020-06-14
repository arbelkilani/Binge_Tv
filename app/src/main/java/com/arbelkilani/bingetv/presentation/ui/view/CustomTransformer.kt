package com.arbelkilani.bingetv.presentation.ui.view

import android.content.Context
import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs


class CustomTransformer : ViewPager.PageTransformer {

    private val TAG = CustomTransformer::class.java.simpleName

    private lateinit var viewPager: ViewPager

    override fun transformPage(view: View, position: Float) {

        viewPager = view.parent as ViewPager

        val leftInScreen: Int = view.left - viewPager.scrollX
        val centerXInViewPager: Int = leftInScreen + view.measuredWidth / 2
        val offsetX = centerXInViewPager - viewPager.measuredWidth / 2
        val offsetRate = offsetX.toFloat() * 0.36f / viewPager.measuredWidth
        val scaleFactor = 1 - abs(offsetRate)
        val alphaFactor = 1 - 2 * abs(offsetRate)
        view.alpha = 1f

        if (scaleFactor > 0) {
            //view.scaleX = scaleFactor
            //view.scaleY = scaleFactor
            /*view.translationX =
                -dp2px(
                    view.context,
                    (view.measuredWidth - view.paddingLeft).toFloat()
                ) * offsetRate */
            view.alpha = alphaFactor
            ViewCompat.setElevation(view, 0.0f)
        }

        ViewCompat.setElevation(view, scaleFactor)

    }
}