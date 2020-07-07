package com.arbelkilani.bingetv.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.entities.base.Resource
import com.arbelkilani.bingetv.data.entities.tv.CombinedObjects
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SplashActivity : AppCompatActivity() {

    private val TAG = SplashActivity::class.java.simpleName

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(Constants.SPLASH_DELAY)
            startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
            finish()
        }

        /*splashViewModel.resource.observe(this, Observer {
            when (it.status) {
                SUCCESS -> navigateToDashboard(it)
                ERROR -> Toast.makeText(this, it.message!!, Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    private fun navigateToDashboard(it: Resource<CombinedObjects>) {
        /*startActivity(
            Intent(this, DashboardActivity::class.java)
                .apply {
                    putExtra(Constants.SPLASH_DASHBOARD_DATA, it.data)
                })
        finish()*/
    }

}
