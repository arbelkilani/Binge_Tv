package com.arbelkilani.bingetv.utils

import android.content.Context
import android.view.View

fun dp2px(context: Context, dipValue: Float): Int {
    val m: Float = context.resources.displayMetrics.density
    return (dipValue * m + 0.5f).toInt()
}