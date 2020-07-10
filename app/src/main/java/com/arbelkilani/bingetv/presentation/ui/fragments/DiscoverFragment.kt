package com.arbelkilani.bingetv.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentDiscoverBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.DataLoadStateAdapter
import com.arbelkilani.bingetv.presentation.adapters.OnTheAirAdapter
import com.arbelkilani.bingetv.presentation.adapters.TrendingAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.ui.activities.SearchActivity
import com.arbelkilani.bingetv.presentation.ui.activities.TvDetailsActivity
import com.arbelkilani.bingetv.presentation.ui.view.CustomTransformer
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.getMenuItemAxis
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiscoverFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: DiscoverViewModel by viewModel()

    private lateinit var binding: FragmentDiscoverBinding

    private val onTheAirAdapter = OnTheAirAdapter(this)

    private var trendingJob: Job? = null
    private var onTheAir: Job? = null

    private fun getTrendingList() {
        trendingJob?.cancel()
        trendingJob = lifecycleScope.launch {
            viewModel.getTrending()
                .catch { cause -> Log.i(TAG, "cause = ${cause.localizedMessage}") }
                .collect {
                    binding.viewPager.apply {
                        adapter = TrendingAdapter(it.results, this@DiscoverFragment)
                        currentItem = it.results.size / 2
                        offscreenPageLimit = 3
                        pageMargin = resources.getDimensionPixelOffset(R.dimen.view_pager_margin)
                        setPageTransformer(false, CustomTransformer())
                    }
                }
        }
    }

    private fun getOnTheAirList() {
        onTheAir?.cancel()
        onTheAir = lifecycleScope.launch {
            viewModel.getOnTheAir()
                .catch { cause -> Log.i(TAG, "cause = ${cause.localizedMessage}") }
                .collectLatest {
                    onTheAirAdapter.submitData(it)
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
            R.layout.fragment_discover,
            container,
            false
        )

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as DashboardActivity).supportActionBar?.let {
            it.title = getString(R.string.title_discovery)
        }

        initAdapter()
        getTrendingList()
        getOnTheAirList()

        return binding.root
    }

    private fun initAdapter() {
        binding.rvPopular.adapter = onTheAirAdapter.withLoadStateFooter(
            footer = DataLoadStateAdapter { onTheAirAdapter.retry() })

        onTheAirAdapter.addLoadStateListener { loadState ->

            // Only show the list if refresh succeeds.
            binding.rvPopular.isVisible = loadState.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh.
            binding.rvShimmer.isVisible = loadState.refresh is LoadState.Loading

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    activity,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {

        private const val TAG = "DiscoverFragment"

        @JvmStatic
        fun newInstance() = DiscoverFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this.activity, SearchActivity::class.java).apply {
                    putExtra("x", getMenuItemAxis(item, context)[0])
                    putExtra("y", getMenuItemAxis(item, context)[1])
                })
                requireActivity().overridePendingTransition(0, 0)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity) {
        startActivity(Intent(activity, TvDetailsActivity::class.java)
            .apply {
                putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
            })
    }
}