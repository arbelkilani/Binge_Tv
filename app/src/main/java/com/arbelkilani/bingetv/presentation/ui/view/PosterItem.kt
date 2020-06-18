package com.arbelkilani.bingetv.presentation.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class PosterItem(context: Context, attrs: AttributeSet?) : CardView(context, attrs) {

    override fun setRadius(radius: Float) {
        super.setRadius(16f)
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(Color.RED)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(width, heightMeasureSpec)
    }
}