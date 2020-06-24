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
import com.arbelkilani.bingetv.presentation.listeners.RevealAnimationListener
import kotlin.math.max


class RevealAnimation(
    _viewGroup: ViewGroup,
    _intent: Intent,
    _activity: Activity,
    _revealAnimationListener: RevealAnimationListener
) {

    val viewGroup = _viewGroup
    val intent = _intent
    val activity = _activity
    var x: Int
    var y: Int
    val revealAnimationListener = _revealAnimationListener

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
        animator.duration = 300
        animator.interpolator = AccelerateInterpolator()
        viewGroup.visibility = View.VISIBLE
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                revealAnimationListener.onRevealEnded()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }

    fun unRevealActivity() {
        val finalRadius = Math.max(viewGroup.width, viewGroup.height) * 1.1f
        val animator = ViewAnimationUtils.createCircularReveal(viewGroup, x, y, finalRadius, 0f)
        animator.duration = 300
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                viewGroup.visibility = View.INVISIBLE
                activity.finish()
            }
        })
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                revealAnimationListener.onUnRevealEnd()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }
}