package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.ui.fragments.DiscoverFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.ProfileFragment
import com.arbelkilani.bingetv.presentation.ui.fragments.WatchlistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = DashboardActivity::class.java.simpleName

    private val discoverFragment = DiscoverFragment()
    private val watchlistFragment = WatchlistFragment()
    private val profileFragment = ProfileFragment()
    private var active: Fragment = discoverFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        initViews()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, watchlistFragment)
                .hide(profileFragment)
                .hide(watchlistFragment).commit()
            supportFragmentManager.beginTransaction().add(R.id.main_container, discoverFragment)
                .commit()
        }
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
                return true
            }

            R.id.menu_watch_list -> {
                supportFragmentManager.beginTransaction().hide(active).show(watchlistFragment)
                    .commit()
                active = watchlistFragment
                return true
            }

            R.id.menu_profile -> {
                supportFragmentManager.beginTransaction().hide(active).show(profileFragment)
                    .commit()
                active = profileFragment
                return true
            }
        }

        return false
    }
}