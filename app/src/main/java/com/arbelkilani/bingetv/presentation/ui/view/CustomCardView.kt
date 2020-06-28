package com.arbelkilani.bingetv.presentation.ui.view

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.cardview.widget.CardView


class CustomCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    companion object {
        const val MIN_SCALE = 0.95f
        const val DEFAULT_SCALE = 1f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {

            val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
            val x = it.x
            val y = it.y

            return if (rectF.contains(x, y) && (it.action == MotionEvent.ACTION_DOWN
                        || it.action == MotionEvent.ACTION_MOVE)
            ) {
                scaleX = MIN_SCALE
                scaleY = MIN_SCALE
                true
            } else {
                scaleX = DEFAULT_SCALE
                scaleY = DEFAULT_SCALE
                true
            }
        }
        Log.i("TAG++", "event : ${event!!.action}")

        return super.onTouchEvent(event)

    }
}