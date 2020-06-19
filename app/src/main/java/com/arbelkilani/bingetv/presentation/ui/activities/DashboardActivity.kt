package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.ui.fragments.DiscoverFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.WatchlistFragment
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = DashboardActivity::class.java.simpleName

    private lateinit var airingTodayData: ApiResponse<Tv>
    private lateinit var trendingTv: ApiResponse<Tv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initViews()
        initData()

        if (savedInstanceState == null) {
            loadFragment(
                DiscoverFragment.newInstance(
                    airingTodayData, trendingTv
                ), R.string.title_discovery
            )
        }
    }

    private fun initData() {
        Log.i(TAG, "initData")
        val combinedExtra =
            intent!!.getParcelableExtra<CombinedObjects>(Constants.SPLASH_DASHBOARD)!!
        airingTodayData = combinedExtra.airing
        trendingTv = combinedExtra.trending
    }

    private fun initViews() {
        Log.i(TAG, "initViews()")
        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun loadFragment(fragment: Fragment, title: Int) {
        Log.i(TAG, "loadFragment() : ${fragment::class.java.simpleName}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment::class.java.simpleName)
            .commit()
        toolbar.setTitle(title)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == bottom_navigation.selectedItemId) return false

        when (item.itemId) {
            R.id.menu_discover -> {
                loadFragment(
                    DiscoverFragment.newInstance(
                        airingTodayData, trendingTv
                    ), R.string.title_discovery
                )
                return true
            }

            R.id.menu_watch_list -> {
                loadFragment(WatchlistFragment(), R.string.title_watch_list)
                return true
            }
        }

        return false
    }
}