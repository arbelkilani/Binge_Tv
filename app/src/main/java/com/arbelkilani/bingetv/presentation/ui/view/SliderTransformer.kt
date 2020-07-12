package com.arbelkilani.bingetv.presentation.ui.view

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.arbelkilani.bingetv.utils.convertDpToPixel
import kotlin.math.abs

class SliderTransformer : ViewPager.PageTransformer {

    companion object {
        private const val TAG = "SliderTransformer"
        private const val MIN_SCALE = 0.75f
        private const val CENTER_PAGE_SCALE = 0.8f
    }

    private val scalingValue = 0.2f
    private val minScale = 0.7f
    private val itemGap = 0f

    /*override fun transformPage(page: View, position: Float) {
        val viewPager = page.parent as ViewPager
        val offscreenPageLimit = viewPager.offscreenPageLimit
        val pageWidth = viewPager.width
        val horizontalOffsetBase =
            (pageWidth - pageWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + 15

        if (position >= offscreenPageLimit || position <= -1) {
            page.visibility = View.GONE;
        } else {
            page.visibility = View.VISIBLE;
        }

        if (position >= 0) {
            val translationX = (horizontalOffsetBase - page.width) * position
            page.translationX = translationX;
        }
        if (position > -1 && position < 0) {
            page.alpha = (position * position * position + 1)
        } else if (position > offscreenPageLimit - 1) {
            page.alpha = 1 - position + floor(position)
        } else {
            page.alpha = 1f;
        }
        if (position == 0f) {
            page.scaleX = CENTER_PAGE_SCALE;
            page.scaleY = CENTER_PAGE_SCALE;
        } else {
            val scaleFactor = min(CENTER_PAGE_SCALE - position * .1f, CENTER_PAGE_SCALE)
            page.scaleX = scaleFactor;
            page.scaleY = scaleFactor;
        }
        ViewCompat.setElevation(page, (offscreenPageLimit - position) * 5);
    }*/

    /*override fun transformPage(page: View, position: Float) {
        if (position >= 0) {
            page.scaleX = 0.9f - 0.05f * position
            page.scaleY = 0.9f
            page.alpha = 1f - 0.3f * position
            page.translationX = -page.width * position
            page.translationY = -30 * position
        } else {
            page.alpha = 1 + 0.3f * position
            page.scaleX = 0.9f + 0.05f * position
            page.scaleY = 0.9f
            page.translationX = page.width * position
            page.translationY = 30 * position
        }
    }*/

    override fun transformPage(page: View, position: Float) {
        val viewPager = page.parent as ViewPager
        Log.i(TAG, "position = $position")
        page.apply {

            elevation = -abs(position)
            val factor = -.14f * position + 1f

            if (position >= 0) {
                scaleX = factor
                scaleY = factor
                translationX = -(width / 1.3f) * position + convertDpToPixel(20f, page.context)
                alpha = if (position > viewPager.offscreenPageLimit) 0f else factor
            } else {
                //alpha = 1f - scaleFactor
            }

        }
    }
}