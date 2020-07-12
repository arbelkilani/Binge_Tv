package com.arbelkilani.bingetv.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentWatchlistBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.viewpager.WatchlistAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.ui.view.SliderTransformer
import com.arbelkilani.bingetv.presentation.viewmodel.watchlist.WatchlistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            binding.viewPager.apply {
                adapter =
                    WatchlistAdapter(
                        it,
                        this@WatchlistFragment
                    )
                offscreenPageLimit = 3
                //pageMargin = resources.getDimensionPixelOffset(R.dimen.view_pager_margin)
                //currentItem = it.size / 2
                setPageTransformer(false, SliderTransformer())
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

    }
}