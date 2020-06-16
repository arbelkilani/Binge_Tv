package com.arbelkilani.bingetv.presentation.ui.activities

import android.content.ContextWrapper
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginEnd
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.genre.Genre
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.details_bottom_sheet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvActivity : AppCompatActivity() {

    private val TAG = DetailsTvActivity::class.java.simpleName

    private val detailsTvhActivityViewModel: DetailsTvActivityViewModel by viewModel()
    private lateinit var detailsTvBinding: ActivityDetailsTvBinding

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

            val layoutParms = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            layoutParms.marginEnd = 20

            for (genre: Genre in tvDetailsResource.data!!.genres!!) {
                val tv = TextView(ContextThemeWrapper(this, R.style.TextView_Genre), null, 0)
                tv.layoutParams = layoutParms
                tv.text = genre.name
                ll_genres.addView(tv)
            }
        })

        initViews()
    }

    private fun initViews() {
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
    }
}