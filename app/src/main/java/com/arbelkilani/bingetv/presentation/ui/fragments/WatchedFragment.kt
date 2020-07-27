package com.arbelkilani.bingetv.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentWatchedBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.WatchedAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.TvDetailsActivity
import com.arbelkilani.bingetv.presentation.viewmodel.watched.WatchedViewModel
import com.arbelkilani.bingetv.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel

class WatchedFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: WatchedViewModel by viewModel()

    private lateinit var binding: FragmentWatchedBinding

    private val returningAdapter = WatchedAdapter(this)
    private val endedAdapter = WatchedAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_watched,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()

        viewModel.returningSeries.observe(viewLifecycleOwner, Observer {
            (binding.rvReturning.adapter as WatchedAdapter).notifyDataSetChanged(it)
        })

        viewModel.endedSeries.observe(viewLifecycleOwner, Observer {
            (binding.rvEnded.adapter as WatchedAdapter).notifyDataSetChanged(it)
        })


        return binding.root
    }

    private fun initAdapter() {
        binding.rvReturning.adapter = returningAdapter
        binding.rvEnded.adapter = endedAdapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }

    companion object {
        private const val TAG = "WatchedFragment"
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity, position: Int, adapter: String) {
        startActivity(
            Intent(activity, TvDetailsActivity::class.java)
                .apply {
                    putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
                    putExtra(Constants.TV_SHOW_ENTITY_POSITION, position)
                    putExtra(Constants.TV_SHOW_ENTITY_ADAPTER, adapter)
                })
    }

}