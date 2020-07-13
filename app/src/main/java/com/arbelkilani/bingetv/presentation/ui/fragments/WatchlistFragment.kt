package com.arbelkilani.bingetv.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentWatchlistBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.viewpager.WatchlistAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.TvDetailsActivity
import com.arbelkilani.bingetv.presentation.ui.view.SliderTransformer
import com.arbelkilani.bingetv.presentation.viewmodel.watchlist.WatchlistViewModel
import com.arbelkilani.bingetv.utils.Constants
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.min

class WatchlistFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: WatchlistViewModel by viewModel()

    private lateinit var binding: FragmentWatchlistBinding

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
            }
        })

        return binding.root
    }

    companion object {
        private const val TAG = "WatchlistFragment"
        private const val MIN_OFFSCREEN_PAGE_LIMIT = 3

        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity) {
        binding.viewPager.tag = binding.viewPager.currentItem
        startActivity(
            Intent(activity, TvDetailsActivity::class.java)
                .apply {
                    putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
                })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshWatchlist()
    }
}