package com.arbelkilani.bingetv.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.Resource
import com.arbelkilani.bingetv.data.model.base.Status.ERROR
import com.arbelkilani.bingetv.data.model.base.Status.SUCCESS
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.viewmodel.splash.SplashViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
        splashViewModel.resource.observe(this, Observer {
            when (it.status) {
                SUCCESS -> navigateToDashboard(it)
                ERROR -> Toast.makeText(this, it.message!!, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToDashboard(it: Resource<CombinedObjects>) {
        startActivity(
            Intent(this, DashboardActivity::class.java)
                .apply {
                    putExtra(Constants.SPLASH_DASHBOARD, it.data)
                })
        finish()
    }

}
