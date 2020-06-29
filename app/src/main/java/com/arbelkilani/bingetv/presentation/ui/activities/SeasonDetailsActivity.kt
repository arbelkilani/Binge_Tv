package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ActivitySeasonDetailsBinding
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SeasonDetailsActivity : AppCompatActivity() {

    private val TAG = SeasonDetailsActivity::class.java.simpleName

    private val viewModel: SeasonDetailsViewModel by viewModel {
        parametersOf(
            intent.getIntExtra(Constants.TV_ID, 0),
            intent.getParcelableExtra(Constants.SEASON_DETAILS)
        )
    }

    private lateinit var binding: ActivitySeasonDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_season_details)

        binding.viewModel = viewModel
        binding.season = viewModel.season.value
        binding.lifecycleOwner = this

    }
}