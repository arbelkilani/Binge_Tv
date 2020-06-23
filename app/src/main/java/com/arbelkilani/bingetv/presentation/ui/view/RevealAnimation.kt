package com.arbelkilani.bingetv.presentation.ui.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import kotlin.math.max


class RevealAnimation(
    _viewGroup: ViewGroup,
    _intent: Intent,
    _activity: Activity
) {

    val viewGroup = _viewGroup
    val intent = _intent
    val activity = _activity
    var x: Int
    var y: Int

    init {
        viewGroup.visibility = View.INVISIBLE

        x = intent.getIntExtra("x", viewGroup.width)
        y = intent.getIntExtra("y", 0)

        val viewTreeObserver = viewGroup.viewTreeObserver

        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    revealActivity(x, y, viewGroup)
                    viewGroup.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        } else {
            viewGroup.visibility = View.VISIBLE
        }
    }

    private fun revealActivity(x: Int, y: Int, viewGroup: ViewGroup) {
        val finalRadius = max(viewGroup.width, viewGroup.height) * 1.1f
        val animator = ViewAnimationUtils.createCircularReveal(viewGroup, x, y, 0f, finalRadius)
        animator.duration = 400
        animator.interpolator = AccelerateInterpolator()
        viewGroup.visibility = View.VISIBLE
        animator.start()
    }

    fun unRevealActivity() {
        val finalRadius = Math.max(viewGroup.width, viewGroup.height) * 1.1f
        val animator = ViewAnimationUtils.createCircularReveal(viewGroup, x, y, finalRadius, 0f)
        animator.duration = 400
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                viewGroup.visibility = View.INVISIBLE
                activity.finish()
            }
        })
        animator.start()
    }
}