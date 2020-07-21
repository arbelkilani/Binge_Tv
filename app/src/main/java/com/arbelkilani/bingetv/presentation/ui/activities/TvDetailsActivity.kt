package com.arbelkilani.bingetv.presentation.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
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
import kotlinx.android.synthetic.main.flex_boxes_shimmer.*
import kotlinx.android.synthetic.main.shimmer_tv_details_status.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TvDetailsActivity : AppCompatActivity(), OnSeasonClickListener, TvShowDetailsClickListener {

    companion object {
        const val TAG = "TvShowActivity"
    }

    private val getSeasonEntity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                result.data?.let {
                    val season =
                        it.getParcelableExtra<SeasonEntity>(Constants.SEASON_ENTITY_REQUEST)!!
                    (rv_seasons.adapter as SeasonAdapter).notifyItemChanged(season)
                    viewModel.refresh(it.getParcelableExtra(Constants.TV_SHOW_ENTITY_REQUEST)!!)
                }
            }
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
            flex_box_shimmer.stopShimmer()
            it.let {

                if (it.nextEpisodeData != null) {
                    next_episode_cardview.visibility = View.VISIBLE
                }


                if (it.status.isNotEmpty()) {
                    flex_box_shimmer.visibility = View.GONE
                    tv_details_status_shimmer.visibility = View.GONE
                }

            }
        })

        viewModel.credits.observe(this, Observer {
            shimmer_credits.visibility = View.GONE
            if (it.isEmpty())
                credit_label.visibility = View.GONE
            (rv_credits.adapter as CreditAdapter).notifyDataSetChanged(it)
        })

        viewModel.seasons.observe(this, Observer {
            (rv_seasons.adapter as SeasonAdapter).notifyDataSetChanged(it.asReversed())
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
        (rv_seasons.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        //toolbar_seasons.inflateMenu(R.menu.tv_details_seasons_menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val view = findViewById<View>(R.id.action_show_more)
        when (item.itemId) {
            R.id.action_show_more -> {
                showTvShowPopUpWindow(view)
            }

            R.id.action_select_all -> {
                showSeasonsPopUpWindow(view)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSeasonsPopUpWindow(view: View) {
        val axis = IntArray(2)
        view.getLocationInWindow(axis)

        val popupWindow = PopupWindow(this)
        val layout = layoutInflater.inflate(R.layout.layout_seasons_popup_menu, null)

        layout.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        val widthPixels = resources.displayMetrics.widthPixels
        popupWindow.contentView = layout
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, widthPixels - view.width, 0)

        val selectAllView = layout.findViewById<TextView>(R.id.tv_action_select_all)
        selectAllView.setOnClickListener {
            selectAllView.isSelected = !selectAllView.isSelected
            popupWindow.dismiss()
        }
    }

    private fun showTvShowPopUpWindow(view: View) {

        val currentTvShow = viewModel.tvShowEntity.value!!

        val axis = IntArray(2)
        view.getLocationInWindow(axis)

        val popupWindow = PopupWindow(this)
        popupWindow.setWindowLayoutMode(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val layout = layoutInflater.inflate(R.layout.layout_tv_details_popup_menu, null)

        layout.measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        val widthPixels = resources.displayMetrics.widthPixels
        popupWindow.contentView = layout
        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, widthPixels - view.width, 0)

        val watchlistView = layout.findViewById<TextView>(R.id.tv_action_watchlist)
        watchlistView.visibility = if (currentTvShow.watchedCount > 0) View.GONE else View.VISIBLE
        watchlistView.isSelected = currentTvShow.watchlist
        watchlistView.setOnClickListener {
            viewModel.saveWatchlist(!watchlistView.isSelected)
            watchlistView.isSelected = !watchlistView.isSelected
            popupWindow.dismiss()
        }

        val watchedView = layout.findViewById<TextView>(R.id.tv_action_watched)
        watchedView.text =
            if (currentTvShow.inProduction || currentTvShow.watchedCount < currentTvShow.episodeCount && currentTvShow.watchedCount != 0) getString(
                R.string.watching_label
            ) else getString(R.string.watched_label)
        watchedView.isSelected = currentTvShow.watchedCount > 0
        watchedView.setOnClickListener {
            viewModel.saveWatched(!watchedView.isSelected)
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
        getSeasonEntity.launch(Intent(
            this, SeasonDetailsActivity::class.java
        ).apply {
            putExtra(Constants.SEASON_ENTITY, seasonEntity)
            putExtra(Constants.TV_SHOW_ENTITY, viewModel.tvShowEntity.value)
        })
    }

    override fun onWatchedSeasonClicked(view: View, seasonEntity: SeasonEntity) {
        val result = viewModel.seasonWatchState(!(view as ImageView).isSelected, seasonEntity)
        (rv_seasons.adapter as SeasonAdapter).notifyItemChanged(result)
    }

}


