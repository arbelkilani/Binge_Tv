package com.arbelkilani.bingetv.presentation.ui.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.Status.ERROR
import com.arbelkilani.bingetv.data.model.base.Status.SUCCESS
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.presentation.viewmodel.SplashActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    private val splashActivityViewModel: SplashActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //TODO check if we add animation or rather fix the logo position
        //setBounceAnimation()
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
        splashActivityViewModel.resource.observe(this, Observer {
            when (it.status) {
                SUCCESS -> {
                    startActivity(
                        Intent(this, DashboardActivity::class.java)
                            .apply {
                                putExtra(Constants.SPLASH_DASHBOARD, it.data)
                            })
                    finish()
                }
                ERROR -> Toast.makeText(this, it.message!!, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
