package com.arbelkilani.bingetv.presentation.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.enum.HttpStatusCode
import com.arbelkilani.bingetv.data.model.base.Status.SUCCESS
import com.arbelkilani.bingetv.presentation.viewmodel.SplashActivityViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    private val splashActivityViewModel: SplashActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setBounceAnimation()
    }

    private fun setBounceAnimation() {
        ObjectAnimator.ofFloat(iv_logo, View.TRANSLATION_Y, 0f, 100f, 0f)
            .apply {
                interpolator = BounceInterpolator()
                startDelay = 500
                duration = 2500
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.RESTART
                start()
            }
    }

    override fun onResume() {
        super.onResume()
        splashActivityViewModel.fetchData()
        handleStatus()
    }


    private fun handleStatus() {
        splashActivityViewModel.status.observe(this, Observer {
            when (it) {
                HttpStatusCode.SUCCESS -> {
                    Log.i(TAG, "navigate")
                    startActivity(Intent(this, DashboardActivity::class.java).apply {
                        putExtra("DATA", splashActivityViewModel.airingToday.value)
                        putExtra("DATA2", splashActivityViewModel.trendingTv.value)
                    })
                    finish()
                }

                HttpStatusCode.NETWORK_ERROR -> {
                    Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show()
                }

                else -> Toast.makeText(this, "other error", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
