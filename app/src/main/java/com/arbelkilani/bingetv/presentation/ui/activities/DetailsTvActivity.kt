package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.adapters.CreditAdapter
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

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var bottomSheetBehaviorSeasons: BottomSheetBehavior<RelativeLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details_tv)
        binding.viewModel = viewModel

        val selectedTv = intent!!.getParcelableExtra<Tv>(Constants.DISCOVER_DETAILS)!!
        viewModel.getDetails(selectedTv)
        binding.selectedTv = selectedTv

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }
        toolbar.setNavigationOnClickListener { onBackPressed() }

        viewModel.tvDetailsLiveData.observe(this, Observer { tvDetailsResource ->
            tvDetailsResource.data?.let {
                binding.tvDetails = it
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
        rv_seasons.adapter = SeasonAdapter(tvDetails.seasons.asReversed(), this)

        //TODO check if view pager create lag when open interface
        // could be deteted by the animaion of the seasons bottom sheet title
        /*view_pager.apply {
            adapter = ImageAdapter(tvDetails.images.backdrops)
            //offscreenPageLimit = 3
        }*/
         */

    }

    private fun initViews() {

        val height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_details_content)
        bottomSheetBehavior.peekHeight = (height * 0.65).toInt()

        bottomSheetBehaviorSeasons = BottomSheetBehavior.from(bottom_sheet_details_seasons)
        bottom_sheet_details_seasons.doOnLayout { view ->
            doOnBottomSheetDetailsSeason(view)
        }
    }


    override fun onSeasonItemClicked(tv: Tv) {

    }

    override fun onBackPressed() {
        when {
            bottomSheetBehaviorSeasons.state == BottomSheetBehavior.STATE_EXPANDED ->
                bottomSheetBehaviorSeasons.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED -> {
                bottom_sheet_details_content.smoothScrollTo(0, 0)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            else -> super.onBackPressed()
        }
    }
}


