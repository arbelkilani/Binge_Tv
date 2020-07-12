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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WatchlistFragment : Fragment(), OnTvShowClickListener {

    private val viewModel: WatchlistViewModel by viewModel()

    private lateinit var binding: FragmentWatchlistBinding

    private val watchlistAdapter = WatchlistAdapter(this)

    private var currentItem = 0

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

        binding.viewPager.adapter = watchlistAdapter
        binding.viewPager.offscreenPageLimit = 3
        (binding.viewPager.getChildAt(0) as RecyclerView).overScrollMode =
            View.OVER_SCROLL_NEVER
        binding.viewPager.setPageTransformer(SliderTransformer(3))

        viewModel.watchlist.observe(viewLifecycleOwner, Observer {
            it?.apply {
                (binding.viewPager.adapter as WatchlistAdapter).notifyDataSetChanged(it)
                binding.viewPager.setPageTransformer(SliderTransformer(3))

            }
        })

        return binding.root
    }

    companion object {
        private const val TAG = "WatchlistFragment"

        @JvmStatic
        fun newInstance() = WatchlistFragment()
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity) {
        currentItem = binding.viewPager.currentItem
        startActivity(
            Intent(activity, TvDetailsActivity::class.java)
                .apply {
                    putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
                })
    }

    override fun onResume() {
        super.onResume()
        viewModel.refreshWatchlist()

        //TODO try to perform this in order to prevent transform bug
        binding.viewPager.setCurrentItem(0, true)
        CoroutineScope(Dispatchers.IO).launch {
            delay(50)
            binding.viewPager.setCurrentItem(currentItem, true)
        }

    }
}