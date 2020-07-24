package com.arbelkilani.bingetv.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.state.observe(this, Observer {
            if (it) {
                startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                finish()
            }
        })
    }
}
