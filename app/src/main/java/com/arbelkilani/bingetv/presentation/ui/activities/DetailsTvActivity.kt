package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.ActivityDetailsTvBinding
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_tv.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvActivity : AppCompatActivity() {

    private val TAG = DetailsTvActivity::class.java.simpleName

    private val detailsTvhActivityViewModel: DetailsTvActivityViewModel by viewModel()
    private lateinit var detailsTvBinding: ActivityDetailsTvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        detailsTvBinding = DataBindingUtil.setContentView(this, R.layout.activity_details_tv)

        detailsTvhActivityViewModel.getDetails(intent!!.getParcelableExtra(Constants.DISCOVER_DETAILS)!!)
        detailsTvhActivityViewModel.resource.observe(this, Observer {
            detailsTvBinding.tvDetails = it.data
        })
    }
}