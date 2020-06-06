package com.arbelkilani.bingetv.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = SplashActivity::class.java.simpleName

    private lateinit var airingTodayData : ApiResponse<Tv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        airingTodayData = intent.getParcelableExtra("DATA")!!

        if (savedInstanceState == null) {
            loadFragment(DiscoverFragment.newInstance(airingTodayData))
        }

        initViews()

    }

    private fun initViews() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment::class.java.simpleName)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_discover -> {
                loadFragment(DiscoverFragment())
                return true
            }

            R.id.menu_watch_list -> {
                loadFragment(WatchlistFragment())
                return true
            }
        }

        return false
    }
}