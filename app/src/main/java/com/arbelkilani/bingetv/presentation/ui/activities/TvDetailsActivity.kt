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
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.domain.entities.season.SeasonEntity
import com.arbelkilani.bingetv.presentation.adapters.CreditAdapter
import com.arbelkilani.bingetv.presentation.adapters.SeasonAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.arbelkilani.bingetv.presentation.listeners.TvShowDetailsClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.TvDetailsActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.doOnBottomSheetDetailsSeason
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.details_bottom_sheet_content.*
import kotlinx.android.synthetic.main.details_bottom_sheet_seasons.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvDetailsActivity : AppCompatActivity(), OnSeasonClickListener, TvShowDetailsClickListener {

    companion object {
        const val TAG = "TvShowActivity"
    }

    private val viewModel: TvDetailsActivityViewModel by viewModel {
        parametersOf(intent.getParcelableExtra(Constants.TV_SHOW_ENTITY)!!)
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
        binding.tvShowListener = this
        binding.lifecycleOwner = this

        viewModel.tvShowEntity.observe(this, Observer {
            binding.tvShowEntity = it
            (rv_seasons.adapter as SeasonAdapter).notifyDataSetChanged(it.seasons.asReversed())
        })

        viewModel.credits.observe(this, Observer {
            (rv_credits.adapter as CreditAdapter).notifyDataSetChanged(it)
        })

        initToolbar()
        initAdapter()
        initBottomSheets()

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

        val currentTvShow = viewModel.tvShowEntity.value!!

        val axis = IntArray(2)
        view.getLocationInWindow(axis)

        val popupWindow = PopupWindow(this)
        val layout = layoutInflater.inflate(R.layout.layout_tv_details_popup_menu, null)

        layout.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        val widthPixels = resources.displayMetrics.widthPixels
        popupWindow.contentView = layout
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, widthPixels - view.width, 0)


        /*val watchlistView = layout.findViewById<TextView>(R.id.tv_action_watchlist)
        watchlistView.updateWatchlistState(currentTvShow.watchlist)
        watchlistView.setOnClickListener {
            watchlistView.updateWatchlistState(!currentTvShow.watchlist)
            //viewModel.saveWatchlist(!currentTvShow.watchlist)
            (rv_seasons.adapter as SeasonAdapter).notifyDataSetChanged(currentTvShow.seasons.asReversed()) // TODO problem with updating data on UI
        }*/

        val watchedView = layout.findViewById<TextView>(R.id.tv_action_watched)
        watchedView.isSelected = currentTvShow.watched
        watchedView.setOnClickListener {
            viewModel.isTvShowWatched(!watchedView.isSelected)
            watchedView.isSelected = !watchedView.isSelected
            popupWindow.dismiss()
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

    override fun openHomePage(url: String) {
        if (url.isEmpty())
            return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun playTrailer(key: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key")))
    }

    override fun onSeasonItemClicked(seasonEntity: SeasonEntity) {
        /*startActivity(Intent(this, SeasonDetailsActivity::class.java)
            .apply {
                putExtra(Constants.SEASON_DETAILS, season)
                putExtra(Constants.SELECTED_TV, viewModel.selectedTv.value)
            })*/
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



