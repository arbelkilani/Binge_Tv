package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.adapters.ImageAdapter
import com.arbelkilani.bingetv.presentation.adapters.SeasonAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnSeasonClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_details_tv.*
import kotlinx.android.synthetic.main.details_bottom_sheet.*
import kotlinx.android.synthetic.main.details_content_main.*
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

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }

        detailsTvhActivityViewModel.resource.observe(this, Observer { tvDetailsResource ->
            detailsTvBinding.tvDetails = tvDetailsResource.data
            detailsTvBinding.detailsViewModel = detailsTvhActivityViewModel
            tvDetailsResource.data?.let { tvDetails ->

                rv_seasons.adapter = SeasonAdapter(tvDetails.seasons.asReversed(), this)

                view_pager.apply {
                    adapter = ImageAdapter(tvDetails.images.backdrops)
                    offscreenPageLimit = 3
                }
            }


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
                val textView =
                    TextView(ContextThemeWrapper(this, R.style.TextView_Network), null, 0)
                textView.text = network.name
                fl_networks.addView(textView)

                //TODO re-think showing original icons of network or not
                // problem dark model with dark logos like HBO
                // think about using style for image view
                /*val imageView = ImageView(this)
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                imageView.layoutParams = params
                imageView.layoutParams.height = convertDpToPixel(25f, this).toInt()
                imageView.layoutParams.width = imageView.layoutParams.height * 2
                imageView.setPadding(convertDpToPixel(2f, this).toInt())
                imageView.requestLayout()

                Picasso.get().load(network.logoPath).fit().centerInside().into(imageView)
                fl_networks.addView(imageView) */

            }
        })

        detailsTvhActivityViewModel.trailerKey.observe(this, Observer { key ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key")))
        })

        detailsTvhActivityViewModel.homePageUrl.observe(this, Observer { url ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
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
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottom_sheet.smoothScrollTo(0, 0)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else
            super.onBackPressed()
    }
}


