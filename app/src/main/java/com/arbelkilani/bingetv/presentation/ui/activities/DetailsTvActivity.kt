package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.adapters.SeasonAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.details_bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvActivity : AppCompatActivity(), OnSeasonClickListener {

    private val TAG = DetailsTvActivity::class.java.simpleName

    private val detailsTvhActivityViewModel: DetailsTvActivityViewModel by viewModel()
    private lateinit var detailsTvBinding: ActivityDetailsTvBinding

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<NestedScrollView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        detailsTvBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_tv)

        val selectedTv = intent!!.getParcelableExtra<Tv>(Constants.DISCOVER_DETAILS)!!
        detailsTvhActivityViewModel.getDetails(selectedTv)
        detailsTvBinding.selectedTv = selectedTv

        detailsTvhActivityViewModel.resource.observe(this, Observer { tvDetailsResource ->
            detailsTvBinding.tvDetails = tvDetailsResource.data

            //TODO work on custom tag view
            //TODO check if genres is empty

            fl_genres.removeAllViews()
            for (genre in tvDetailsResource.data!!.genres) {
                val tv = TextView(ContextThemeWrapper(this, R.style.TextView_Genre), null, 0)
                tv.text = genre.name
                fl_genres.addView(tv)
            }

            fl_networks.removeAllViews()
            for (network in tvDetailsResource.data.networks) {
                val tv = TextView(ContextThemeWrapper(this, R.style.TextView_Network), null, 0)
                tv.text = network.name
                fl_networks.addView(tv)
            }

            //TODO data binding recyclerview
            rv_seasons.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DetailsTvActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = SeasonAdapter(
                    tvDetailsResource.data.seasons.asReversed(),
                    this@DetailsTvActivity
                )
            }
        })

        initViews()
    }

    private fun initViews() {
        val height = Resources.getSystem().displayMetrics.heightPixels
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.peekHeight = (height * 0.65).toInt()
    }

    override fun onSeasonItemClicked(tv: Tv) {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        if(bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        else
            super.onBackPressed()
    }
}