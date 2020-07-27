package com.arbelkilani.bingetv.presentation.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentWatchlistBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.RecommendationsAdapter
import com.arbelkilani.bingetv.presentation.adapters.viewpager.WatchlistAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.TvDetailsActivity
import com.arbelkilani.bingetv.presentation.ui.view.SliderTransformer
import com.arbelkilani.bingetv.presentation.viewmodel.watchlist.WatchlistViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.min

class WatchlistFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: WatchlistViewModel by viewModel()

    private lateinit var binding: FragmentWatchlistBinding

    private val recommendationsAdapter = RecommendationsAdapter(this)

    private var recommendationsJob: Job? = null
    private fun recommendations(position: Int) {
        recommendationsJob?.cancel()
        recommendationsJob = lifecycleScope.launch {
            viewModel.recommendations(position)
                .catch { cause -> cause.localizedMessage }
                .collectLatest {
                    recommendationsAdapter.submitData(it)
                }
            binding.rvRelated.smoothScrollToPosition(0)
        }
    }

    private val getTvShowEntity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                result.data?.let {
                    val tvShow =
                        it.getParcelableExtra<TvShowEntity>(Constants.TV_SHOW_ENTITY_REQUEST)!!
                    val position = it.getIntExtra(Constants.TV_SHOW_ENTITY_POSITION_REQUEST, -1)

                    recommendationsAdapter.notifyTvShow(position, tvShow)
                }
            }
        }

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
            R.layout.fragment_watchlist,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initAdapter()

        viewModel.watchlist.observe(viewLifecycleOwner, Observer {
            it?.apply {
                val pageLimit =
                    min(
                        MIN_OFFSCREEN_PAGE_LIMIT,
                        if (it.isEmpty()) MIN_OFFSCREEN_PAGE_LIMIT else it.size
                    )

                binding.viewPager.apply {
                    offscreenPageLimit = pageLimit
                    adapter = WatchlistAdapter(it, this@WatchlistFragment)
                    (getChildAt(0) as RecyclerView).overScrollMode = View.OVER_SCROLL_NEVER
                    setPageTransformer(SliderTransformer(pageLimit))
                    setCurrentItem(if (tag == null) 0 else tag as Int, false)
                }

                binding.viewPager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {

                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        recommendations(position)
                    }
                })
            }
        })

        return binding.root
    }

    private fun initAdapter() {
        binding.rvRelated.adapter = recommendationsAdapter
    }

    companion object {
        private const val TAG = "WatchlistFragment"
        private const val MIN_OFFSCREEN_PAGE_LIMIT = 3
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity, position: Int, adapter: String) {
        binding.viewPager.tag = binding.viewPager.currentItem
        getTvShowEntity.launch(Intent(
            activity, TvDetailsActivity::class.java
        ).apply {
            putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
            putExtra(Constants.TV_SHOW_ENTITY_POSITION, position)
            putExtra(Constants.TV_SHOW_ENTITY_ADAPTER, adapter)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refresh()
    }
}