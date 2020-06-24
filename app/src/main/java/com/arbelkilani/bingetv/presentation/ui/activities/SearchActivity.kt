package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.adapters.TvSearchAdapter
import com.arbelkilani.bingetv.presentation.listeners.KeyboardListener
import com.arbelkilani.bingetv.presentation.listeners.RevealAnimationListener
import com.arbelkilani.bingetv.presentation.ui.view.GridAutoFitLayoutManager
import com.arbelkilani.bingetv.presentation.ui.view.RevealAnimation
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.utils.hideKeyboard
import com.arbelkilani.bingetv.utils.interceptKeyboardVisibility
import com.arbelkilani.bingetv.utils.showKeyboard
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity(), TextWatcher, KeyboardListener, RevealAnimationListener {

    private val TAG = SearchActivity::class.java.simpleName

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var revealAnimation: RevealAnimation
    lateinit var closeMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()

        searchViewModel.tvListLiveData.observe(this, Observer {
            (rv_shows.adapter as TvSearchAdapter).notify(it)
        })
    }

    private fun initViews() {
        revealAnimation = RevealAnimation(root_layout, intent, this, this)
        initToolbar()
        edit_text_search.addTextChangedListener(this)
        interceptKeyboardVisibility(this)

        rv_shows.apply {
            adapter = TvSearchAdapter()
            itemAnimator = DefaultItemAnimator()
            (itemAnimator as DefaultItemAnimator).changeDuration = 0

        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_search)
        supportActionBar?.let {
            it.title = getString(R.string.search_title)
        }

        toolbar_search.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        unRevealAnimation()
    }

    private fun unRevealAnimation() {
        revealAnimation.unRevealActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_activity, menu)
        closeMenuItem = menu!!.getItem(0)
        closeMenuItem.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> edit_text_search.text.clear()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun afterTextChanged(s: Editable?) {
        Log.i(TAG, "afterTextChanged = $s")
        if (s!!.isEmpty())
            closeMenuItem.isVisible = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.i(TAG, "beforeTextChanged = $s")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        closeMenuItem.isVisible = true
        if (start >= 3) {
            searchViewModel.searchTvShow(s.toString())
        }
    }

    override fun onKeyboardShown(currentKeyboardHeight: Int) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_shows.layoutManager = linearLayoutManager
    }

    override fun onKeyboardHidden() {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val gridLayoutManager = GridAutoFitLayoutManager(this, 160)
        rv_shows.layoutManager = gridLayoutManager
        hideKeyboard()
        return true
    }

    override fun onRevealEnded() {
        showKeyboard(root_layout)
    }

    override fun onUnRevealEnd() {
        overridePendingTransition(0, 0)
        hideKeyboard()
    }

}

