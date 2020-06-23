package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.ui.view.RevealAnimation
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var revealAnimation: RevealAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initData()
    }

    private fun initData() {
        revealAnimation = RevealAnimation(root_layout, intent, this)
        setSupportActionBar(toolbar_search)
        supportActionBar?.let {
            it.title = getString(R.string.search_title)
        }

        toolbar_search.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        overridePendingTransition(0, 0)
        revealAnimation.unRevealActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_toolbar_menu, menu)
        return true
    }

}