package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ActivitySearchBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.SearchAdapter
import com.arbelkilani.bingetv.presentation.adapters.dataload.DataLoadStateAdapter
import com.arbelkilani.bingetv.presentation.listeners.KeyboardListener
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.listeners.RevealAnimationListener
import com.arbelkilani.bingetv.presentation.ui.view.RevealAnimation
import com.arbelkilani.bingetv.presentation.viewmodel.SearchViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.hideKeyboard
import com.arbelkilani.bingetv.utils.interceptKeyboardVisibility
import com.arbelkilani.bingetv.utils.showKeyboard
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity(), TextWatcher, KeyboardListener, RevealAnimationListener,
    OnTvShowClickListener {

    private val TAG = SearchActivity::class.java.simpleName

    private lateinit var revealAnimation: RevealAnimation
    var closeMenuItem: MenuItem? = null

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    private val searchAdapter = SearchAdapter(this)

    private var searchJob: Job? = null
    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.search(query)
                .catch { cause -> Log.i(TAG, "cause = ${cause.localizedMessage}") }
                .collectLatest {
                    binding.rvShows.scrollToPosition(0)
                    searchAdapter.submitData(it)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        initAdapter()
        initViews()

    }

    private fun initAdapter() {
        binding.rvShows.adapter = searchAdapter.withLoadStateFooter(
            footer = DataLoadStateAdapter { searchAdapter.retry() }
        )
        binding.rvShows.itemAnimator = DefaultItemAnimator()
        (binding.rvShows.itemAnimator as DefaultItemAnimator).changeDuration = 0
    }

    private fun initViews() {
        revealAnimation = RevealAnimation(binding.rootLayout, intent, this, this)
        initToolbar()
        binding.editTextSearch.addTextChangedListener(this)
        interceptKeyboardVisibility(this)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarSearch)
        supportActionBar?.let {
            it.title = getString(R.string.search_title)
        }

        binding.toolbarSearch.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() {
        unRevealAnimation()
    }

    private fun unRevealAnimation() {
        revealAnimation.unRevealActivity()
        hideKeyboard()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_activity, menu)
        closeMenuItem = menu?.getItem(0)
        closeMenuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_close -> binding.editTextSearch.text.clear()
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
            closeMenuItem?.isVisible = false

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        Log.i(TAG, "beforeTextChanged = $s")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        closeMenuItem?.isVisible = true
        search(s.toString())
    }

    override fun onKeyboardShown(currentKeyboardHeight: Int) {

    }


    override fun onKeyboardHidden() {
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideKeyboard()
        return true
    }

    override fun onRevealEnded() {
        showKeyboard(binding.rootLayout)
    }

    override fun onUnRevealEnd() {
        overridePendingTransition(0, 0)
        hideKeyboard()
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity) {
        startActivity(
            Intent(this, TvDetailsActivity::class.java)
                .apply {
                    putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
                })
    }

}

