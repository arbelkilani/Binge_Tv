package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.presentation.adapters.TvSearchAdapter
import com.arbelkilani.bingetv.presentation.ui.view.RevealAnimation
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.utils.hideKeyboard
import com.arbelkilani.bingetv.utils.showKeyboard
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity(), TextWatcher {

    private val TAG = SearchActivity::class.java.simpleName

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var revealAnimation: RevealAnimation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initViews()

        searchViewModel.tvListLiveData.observe(this, Observer {
            rv_shows.adapter = TvSearchAdapter(it)
        })
    }

    private fun initViews() {
        showRevealAnimation()
        showKeyboard(root_layout)
        initToolbar()
        edit_text_search.addTextChangedListener(this)
    }

    private fun showRevealAnimation() {
        revealAnimation = RevealAnimation(root_layout, intent, this)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_search)
        supportActionBar?.let {
            it.title = getString(R.string.search_title)
        }

        toolbar_search.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        hideKeyboard()
        reverseRevealAnimation()
    }

    private fun reverseRevealAnimation() {
        overridePendingTransition(0, 0)
        revealAnimation.unRevealActivity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        showKeyboard(edit_text_search)
    }

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun afterTextChanged(s: Editable?) {
        Log.i(TAG, "s = $s")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.i(TAG, "s = $s")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (start >= 3)
            searchViewModel.searchTvShow(s.toString())
    }

}