package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.season.Season
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.adapters.CreditAdapter
import com.arbelkilani.bingetv.presentation.adapters.SeasonAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.doOnBottomSheetDetailsSeason
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_details_tv.*
import kotlinx.android.synthetic.main.details_bottom_sheet_content.*
import kotlinx.android.synthetic.main.details_bottom_sheet_seasons.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvActivity : AppCompatActivity(), OnSeasonClickListener {

    private val TAG = DetailsTvActivity::class.java.simpleName

    private val viewModel: DetailsTvActivityViewModel by viewModel()
    private lateinit var binding: ActivityDetailsTvBinding

    private lateinit var bottomSheetBehaviorContent: BottomSheetBehavior<NestedScrollView>
    private lateinit var bottomSheetBehaviorSeasons: BottomSheetBehavior<FrameLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_tv)
        binding.viewModel = viewModel

        val selectedTv = intent!!.getParcelableExtra<Tv>(Constants.DISCOVER_DETAILS)!!
        viewModel.getDetails(selectedTv)
        binding.selectedTv = selectedTv

        initializeToolbars()

        viewModel.tvDetailsLiveData.observe(this, Observer { tvDetailsResource ->
            tvDetailsResource.data?.let {
                binding.tvDetails = it
                rv_seasons.adapter = SeasonAdapter(it.seasons.asReversed(), this)
            }
        })

        viewModel.nextEpisodeData.observe(this, Observer { nextEpisodeData ->
            binding.nextEpisodeData = nextEpisodeData
        })

        viewModel.trailerKey.observe(this, Observer { key ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key")))
        })

        viewModel.homePageUrl.observe(this, Observer { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        })

        viewModel.creditsLiveData.observe(this, Observer { credits ->
            credits?.let {
                rv_credits.adapter = CreditAdapter(it)
            }
        })

        initViews()

        /*

        //TODO check if view pager create lag when open interface
        // could be deteted by the animaion of the seasons bottom sheet title
        /*view_pager.apply {
            adapter = ImageAdapter(tvDetails.images.backdrops)
            //offscreenPageLimit = 3
        }*/
         */

    }

    private fun initializeToolbars() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        setSupportActionBar(toolbar_seasons)
        toolbar_seasons.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initViews() {

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
                putExtra(Constants.TV_ID, viewModel.tvId.value)
            })
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

