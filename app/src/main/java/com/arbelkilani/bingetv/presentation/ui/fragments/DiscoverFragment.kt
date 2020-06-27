package com.arbelkilani.bingetv.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.CombinedObjects
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.databinding.FragmentDiscoverBinding
import com.arbelkilani.bingetv.presentation.adapters.DiscoverAdapter
import com.arbelkilani.bingetv.presentation.adapters.TrendingAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.ui.activities.DetailsTvActivity
import com.arbelkilani.bingetv.presentation.ui.activities.SearchActivity
import com.arbelkilani.bingetv.presentation.ui.view.CustomTransformer
import com.arbelkilani.bingetv.presentation.viewmodel.discover.DiscoverViewModel
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.getMenuItemAxis
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DiscoverFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: DiscoverViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable(Constants.SPLASH_DASHBOARD)
        )
    }

    private lateinit var binding: FragmentDiscoverBinding

    private val trendingAdapter = TrendingAdapter(this)

    private var listJob: Job? = null
    private fun getTrendingList() {
        listJob?.cancel()
        listJob = lifecycleScope.launch {
            viewModel.getTvShows().collectLatest {
                trendingAdapter.submitData(it)
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
        Log.i(TAG, "onCreateView()")

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_discover,
            container,
            false
        )

        viewModel.trending.observe(viewLifecycleOwner, Observer {
            binding.viewPager.apply {
                adapter = DiscoverAdapter(it)
                currentItem = Int.MAX_VALUE / 2
                overScrollMode = 2
                offscreenPageLimit = 3
                pageMargin = resources.getDimensionPixelOffset(R.dimen.view_pager_margin)
                setPageTransformer(false, CustomTransformer())
            }
        })

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        (activity as DashboardActivity).supportActionBar?.let {
            it.title = getString(R.string.title_discovery)
        }

        initAdapter()
        getTrendingList()

        return binding.root
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = trendingAdapter
    }

    companion object {

        private const val TAG = "DiscoverFragment"

        @JvmStatic
        fun newInstance(combinedObjects: CombinedObjects) =
            DiscoverFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(Constants.SPLASH_DASHBOARD, combinedObjects)
                    }
                }
    }

    override fun onTvItemClicked(tv: Tv) {
        startActivity(Intent(activity, DetailsTvActivity::class.java)
            .apply {
                putExtra(Constants.DISCOVER_DETAILS, tv)
            })
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
}