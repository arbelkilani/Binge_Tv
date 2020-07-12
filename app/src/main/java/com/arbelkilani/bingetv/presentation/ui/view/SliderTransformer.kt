package com.arbelkilani.bingetv.presentation.ui.view

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.arbelkilani.bingetv.utils.convertDpToPixel
import kotlin.math.abs

class SliderTransformer : ViewPager.PageTransformer {

    companion object {
        private const val TAG = "SliderTransformer"

        private const val DEFAULT_TRANSLATION_X = .0f
        private const val DEFAULT_TRANSLATION_FACTOR = 1.3f
        private const val DEFAULT_TRANSLATION_OFFSET = 20f

        private const val SCALE_FACTOR = .14f
        private const val DEFAULT_SCALE = 1f

        private const val ALPHA_FACTOR = .3f
        private const val DEFAULT_ALPHA = 1f
        private const val DEFAULT_ALPHA_OFFSET = .94f
    }

    override fun transformPage(page: View, position: Float) {

        val viewPager = page.parent as ViewPager

        page.apply {

            elevation = -abs(position)

            val scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE
            val alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA

            page.isClickable = !(position <= 0 || position >= 1)

            when {
                position <= 0f -> {
                    translationX = DEFAULT_TRANSLATION_X
                    scaleX = DEFAULT_SCALE
                    scaleY = DEFAULT_SCALE
                    alpha = position + DEFAULT_ALPHA_OFFSET
                }
                position <= viewPager.offscreenPageLimit -> {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    translationX =
                        -(width / DEFAULT_TRANSLATION_FACTOR) * position + convertDpToPixel(
                            DEFAULT_TRANSLATION_OFFSET,
                            page.context
                        )
                    alpha = alphaFactor
                }
                else -> {
                    translationX = DEFAULT_TRANSLATION_X
                    scaleX = DEFAULT_SCALE
                    scaleY = DEFAULT_SCALE
                    alpha = DEFAULT_ALPHA
                }
            }
        }
    }
}