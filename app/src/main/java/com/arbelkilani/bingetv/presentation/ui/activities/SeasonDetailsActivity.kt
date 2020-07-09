package com.arbelkilani.bingetv.presentation.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.entities.episode.EpisodeData
import com.arbelkilani.bingetv.databinding.ActivitySeasonDetailsBinding
import com.arbelkilani.bingetv.databinding.EpisodeDetailsBottomSheetBinding
import com.arbelkilani.bingetv.domain.entities.episode.EpisodeEntity
import com.arbelkilani.bingetv.presentation.adapters.EpisodeAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnEpisodeClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.season.SeasonDetailsViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.episode_details_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SeasonDetailsActivity : AppCompatActivity(), OnEpisodeClickListener {

    companion object {
        private const val TAG = "SeasonDetailsActivity"
    }

    private val viewModel: SeasonDetailsViewModel by viewModel {
        parametersOf(
            intent.getParcelableExtra(Constants.SEASON_ENTITY),
            intent.getParcelableExtra(Constants.TV_SHOW_ENTITY)
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
        binding.lifecycleOwner = this


        viewModel.season.observe(this, Observer {
            binding.seasonEntity = it
        })

        viewModel.tvShow.observe(this, Observer {
            binding.tvShowEntity = it
        })

        viewModel.episodes.observe(this, Observer {
            (rv_episodes.adapter as EpisodeAdapter).notifyDataSetChanged(it.asReversed())
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
        rv_episodes.adapter = episodeAdapter
        (rv_episodes.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

    }

    private fun initBottomSheet() {

        detailsBinding =
            DataBindingUtil.inflate<EpisodeDetailsBottomSheetBinding>(
                LayoutInflater.from(this), R.layout.episode_details_bottom_sheet, null, false
            )
        //detailsBinding.season = viewModel.season.value
        //detailsBinding.tvShow = viewModel.selectedTv.value

        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(detailsBinding.root)

    }

    override fun onEpisodeClicked(episodeData: EpisodeData) {
        //detailsBinding.episode = episodeData
        //bottomSheetDialog.show()
    }

    override fun onWatchedEpisodeClicked(view: View, episodeEntity: EpisodeEntity) {
        val result = viewModel.episodeWatchState(!(view as ImageView).isSelected, episodeEntity)
        (rv_episodes.adapter as EpisodeAdapter).notifyItemChanged(result)
    }
}