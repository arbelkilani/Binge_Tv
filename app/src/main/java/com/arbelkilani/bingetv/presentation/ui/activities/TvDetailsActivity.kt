package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.adapters.CreditAdapter
import com.arbelkilani.bingetv.presentation.adapters.SeasonAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.TvDetailsActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.doOnBottomSheetDetailsSeason
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.details_bottom_sheet_content.*
import kotlinx.android.synthetic.main.details_bottom_sheet_seasons.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvDetailsActivity : AppCompatActivity(), OnSeasonClickListener {

    private val TAG = TvDetailsActivity::class.java.simpleName

    private val viewModel: TvDetailsActivityViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(Constants.DISCOVER_DETAILS)!!)
    }

    private lateinit var binding: ActivityDetailsTvBinding

    private lateinit var bottomSheetBehaviorContent: BottomSheetBehavior<NestedScrollView>
    private lateinit var bottomSheetBehaviorSeasons: BottomSheetBehavior<FrameLayout>

    private val creditsAdapter = CreditAdapter()
    private val seasonsAdapter = SeasonAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_tv)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.selectedTv = viewModel.selectedTv.value

        viewModel.credits.observe(this, Observer {
            (rv_credits.adapter as CreditAdapter).notifyDataSetChanged(it)
        })

        viewModel.tvShow.observe(this, Observer {
            it.data?.let { tvShow ->
                binding.tvDetails = tvShow
                (rv_seasons.adapter as SeasonAdapter).notifyDataSetChanged(tvShow.seasons.asReversed())
            }
        })

        viewModel.nextEpisodeData.observe(this, Observer {
            binding.nextEpisodeData = it
        })

        initToolbar()
        initAdapter()
        initBottomSheets()

        viewModel.trailerKey.observe(this, Observer { key ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key")))
        })

        viewModel.homePageUrl.observe(this, Observer { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        })

        /*

        //TODO check if view pager create lag when open interface
        // could be deteted by the animaion of the seasons bottom sheet title
        /*view_pager.apply {
            adapter = ImageAdapter(tvDetails.images.backdrops)
            //offscreenPageLimit = 3
        }*/
         */

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            title = ""
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        setSupportActionBar(toolbar_seasons)
        toolbar_seasons.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initAdapter() {
        rv_credits.adapter = creditsAdapter
        rv_seasons.adapter = seasonsAdapter
    }

    private fun initBottomSheets() {

        val height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehaviorContent = BottomSheetBehavior.from(bottom_sheet_details_content)
        bottomSheetBehaviorContent.peekHeight = (height * 0.65).toInt()
        bottomSheetBehaviorContent.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val drawable = bottomSheet.background.mutate() as GradientDrawable
                val cornerRadius =
                    resources.getDimension(R.dimen.details_bottom_sheet_corner_radius)
                drawable.cornerRadius = cornerRadius * (1.0f - slideOffset)
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.i(TAG, "onStateChanged : $newState")
            }
        })

        bottomSheetBehaviorSeasons = BottomSheetBehavior.from(bottom_sheet_details_seasons)

        bottom_sheet_details_seasons.doOnLayout { view ->
            doOnBottomSheetDetailsSeason(view)
        }
    }


    override fun onSeasonItemClicked(season: Season) {
        startActivity(Intent(this, SeasonDetailsActivity::class.java)
            .apply {
                putExtra(Constants.SEASON_DETAILS, season)
                putExtra(Constants.SELECTED_TV, viewModel.selectedTv.value)
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        binding.toolbar.inflateMenu(R.menu.tv_details_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val view = findViewById<View>(R.id.action_show_more)
        when (item.itemId) {
            R.id.action_show_more -> {
                showPopUpMenu(view)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopUpMenu(view: View) {

        val currentTvShow = viewModel.tvShow.value?.data!!

        val popupWindow = PopupWindow(this)
        val layout = layoutInflater.inflate(R.layout.layout_tv_details_popup_menu, null)
        popupWindow.contentView = layout
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAsDropDown(view, -400, 0, Gravity.NO_GRAVITY)

        val watchlistView = layout.findViewById<TextView>(R.id.tv_action_watchlist)
        watchlistView.updateWatchlistState(currentTvShow.watchlist)
        watchlistView.setOnClickListener {
            watchlistView.updateWatchlistState(!currentTvShow.watchlist)
            viewModel.saveWatchlist(!currentTvShow.watchlist)
        }

        val watchedView = layout.findViewById<TextView>(R.id.tv_action_watched)
        watchedView.updateWatchedState(currentTvShow.watched)
        watchedView.setOnClickListener {
            watchedView.updateWatchedState(!currentTvShow.watched)
            viewModel.saveWatched(!currentTvShow.watched)
        }

    }

    override fun onBackPressed() {
        when {
            bottomSheetBehaviorSeasons.state == BottomSheetBehavior.STATE_EXPANDED ->
                bottomSheetBehaviorSeasons.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehaviorContent.state == BottomSheetBehavior.STATE_EXPANDED -> {
                bottom_sheet_details_content.smoothScrollTo(0, 0)
                bottomSheetBehaviorContent.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            else -> super.onBackPressed()
        }
    }
}

private fun TextView.updateWatchlistState(watchlist: Boolean) {
    text = if (watchlist) {
        context.getString(R.string.state_watchlist_true)
    } else {
        context.getString(R.string.state_watchlist_false)
    }
    isSelected = watchlist
}

private fun TextView.updateWatchedState(watched: Boolean) {
    text = if (watched) {
        context.getString(R.string.state_watched_true)
    } else {
        context.getString(R.string.state_watched_false)
    }
    isSelected = watched
}



