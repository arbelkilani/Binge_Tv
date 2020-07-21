package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.ui.fragments.DiscoverFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.ProfileFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.WatchedFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.WatchlistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = DashboardActivity::class.java.simpleName

    private val discoverFragment = DiscoverFragment()
    private val watchlistFragment = WatchlistFragment()
    private val watchedFragment = WatchedFragment()
    private val profileFragment = ProfileFragment()
    private var active: Fragment = discoverFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initViews()

        supportFragmentManager.beginTransaction().add(R.id.main_container, profileFragment)
            .hide(profileFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_container, watchedFragment)
            .hide(watchedFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_container, watchlistFragment)
            .hide(watchlistFragment).commit()
        supportFragmentManager.beginTransaction().add(R.id.main_container, discoverFragment)
            .commit()

        supportActionBar?.title = getString(R.string.title_discovery)

    }

    private fun initViews() {
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(toolbar)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == bottom_navigation.selectedItemId) return false

        when (item.itemId) {
            R.id.menu_discover -> {
                supportFragmentManager.beginTransaction().hide(active).show(discoverFragment)
                    .commit()
                active = discoverFragment
                supportActionBar?.title = getString(R.string.title_discovery)
                return true
            }

            R.id.menu_watch_list -> {
                supportFragmentManager.beginTransaction().hide(active).show(watchlistFragment)
                    .commit()
                active = watchlistFragment
                supportActionBar?.title = getString(R.string.title_watch_list)
                return true
            }

            R.id.menu_watched -> {
                supportFragmentManager.beginTransaction().hide(active).show(watchedFragment)
                    .commit()
                active = watchedFragment
                supportActionBar?.title = getString(R.string.title_watched)
                return true
            }

            R.id.menu_profile -> {
                supportFragmentManager.beginTransaction().hide(active).show(profileFragment)
                    .commit()
                active = profileFragment
                supportActionBar?.title = getString(R.string.title_profile)
                return true
            }
        }

        return false
    }
}