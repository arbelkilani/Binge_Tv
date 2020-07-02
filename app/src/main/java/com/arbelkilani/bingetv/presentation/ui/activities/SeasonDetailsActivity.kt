package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.episode.Episode
import com.arbelkilani.bingetv.databinding.ActivitySeasonDetailsBinding
import com.arbelkilani.bingetv.databinding.EpisodeDetailsBottomSheetBinding
import com.arbelkilani.bingetv.presentation.adapters.EpisodeAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnEpisodeClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.episode_details_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SeasonDetailsActivity : AppCompatActivity(), OnEpisodeClickListener {

    private val TAG = SeasonDetailsActivity::class.java.simpleName

    private val viewModel: SeasonDetailsViewModel by viewModel {
        parametersOf(
            intent.getParcelableExtra(Constants.SELECTED_TV),
            intent.getParcelableExtra(Constants.SEASON_DETAILS)
        )
    }

    private lateinit var binding: ActivitySeasonDetailsBinding
    private lateinit var detailsBinding: EpisodeDetailsBottomSheetBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val episodeAdapter = EpisodeAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_season_details)

        binding.viewModel = viewModel
        binding.season = viewModel.season.value
        binding.tvShow = viewModel.selectedTv.value

        binding.lifecycleOwner = this

        viewModel.episodes.observe(this, Observer {
            (recycler_episodes.adapter as EpisodeAdapter).notifyDataSetChanged(it)
        })

        initAdapter()
        initToolbar()
        initBottomSheet()
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun initAdapter() {
        recycler_episodes.adapter = episodeAdapter
    }

    private fun initBottomSheet() {

        detailsBinding =
            DataBindingUtil.inflate<EpisodeDetailsBottomSheetBinding>(
                LayoutInflater.from(this), R.layout.episode_details_bottom_sheet, null, false
            )
        detailsBinding.season = viewModel.season.value
        detailsBinding.tvShow = viewModel.selectedTv.value

        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(detailsBinding.root)

    }

    override fun onEpisodeClicked(episode: Episode) {
        detailsBinding.episode = episode
        bottomSheetDialog.show()
    }
}