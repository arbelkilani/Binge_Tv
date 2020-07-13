package com.arbelkilani.bingetv.presentation.ui.view

import android.util.Log
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class SliderTransformer(offscreenPageLimit: Int) : ViewPager2.PageTransformer {

    companion object {
        private const val TAG = "SliderTransformer"

        private const val DEFAULT_TRANSLATION_X = .0f
        private const val DEFAULT_TRANSLATION_FACTOR = 1.3f

        private const val SCALE_FACTOR = .14f
        private const val DEFAULT_SCALE = 1f

        private const val ALPHA_FACTOR = .3f
        private const val DEFAULT_ALPHA = 1f

        private const val OFFSET_SCREEN_LIMIT = 2
    }

    private var limit = 0

    init {
        limit = offscreenPageLimit
    }

    override fun transformPage(page: View, position: Float) {

        page.apply {

            elevation = -abs(position)

            val scaleFactor = -SCALE_FACTOR * position + DEFAULT_SCALE
            val alphaFactor = -ALPHA_FACTOR * position + DEFAULT_ALPHA


            Log.i("TAG++", "position = $position")
            when {
                position <= 0f -> {
                    translationX = DEFAULT_TRANSLATION_X
                    scaleX = DEFAULT_SCALE
                    scaleY = DEFAULT_SCALE
                    alpha = DEFAULT_ALPHA + position
                }
                position <= limit - 1 -> {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    translationX =
                        -(width / DEFAULT_TRANSLATION_FACTOR) * position
                    alpha = alphaFactor
                }
                else -> {
                    //translationX = DEFAULT_TRANSLATION_X
                    //scaleX = DEFAULT_SCALE
                    //scaleY = DEFAULT_SCALE
                    alpha = 0f
                }
            }
        }
    }
}