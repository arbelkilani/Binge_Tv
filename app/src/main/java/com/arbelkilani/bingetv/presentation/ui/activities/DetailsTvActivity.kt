package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.viewmodel.DetailsTvActivityViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_tv.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsTvActivity : AppCompatActivity() {

    private val TAG = DetailsTvActivity::class.java.simpleName

    private lateinit var selectedTv: Tv

    private val detailsTvhActivityViewModel: DetailsTvActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tv)

        initViews()
        initData()

        detailsTvhActivityViewModel.resource.observe(this, Observer {
            Log.i(TAG, "resource = $it")
        })
    }

    private fun initData() {
        Picasso.get()
            .load(selectedTv.getPosterPath())
            .into(iv_background)
    }

    private fun initViews() {
        selectedTv = intent!!.getParcelableExtra(Constants.DISCOVER_DETAILS)!!
        detailsTvhActivityViewModel.getDetails(selectedTv)
    }
}