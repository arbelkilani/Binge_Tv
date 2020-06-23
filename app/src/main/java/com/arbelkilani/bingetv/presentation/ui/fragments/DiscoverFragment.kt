package com.arbelkilani.bingetv.presentation.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.adapters.DiscoverAdapter
import com.arbelkilani.bingetv.presentation.adapters.TrendingAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvClickListener
import com.arbelkilani.bingetv.presentation.ui.activities.DashboardActivity
import com.arbelkilani.bingetv.presentation.ui.activities.DetailsTvActivity
import com.arbelkilani.bingetv.presentation.ui.view.CustomTransformer
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment(), OnTvClickListener {

    private val TAG = DiscoverFragment::class.java.simpleName

    private lateinit var airingTodayTvResponse: ApiResponse<Tv>
    private lateinit var trendingTvResponse: ApiResponse<Tv>

    private lateinit var airingTodayList: List<Tv>
    private lateinit var trendingTvList: List<Tv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            airingTodayTvResponse = it.getParcelable(Constants.AIRING_TODAY_PARAM)!!
            airingTodayList = airingTodayTvResponse.results

            trendingTvResponse = it.getParcelable(Constants.TRENDING_PARAM)!!
            trendingTvList = trendingTvResponse.results
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {

        view_pager.apply {
            adapter = DiscoverAdapter(airingTodayList)
            currentItem = Int.MAX_VALUE / 2
            overScrollMode = 2
            offscreenPageLimit = 3
            pageMargin = resources.getDimensionPixelOffset(R.dimen.view_pager_margin)
            setPageTransformer(false, CustomTransformer())
        }

        recycler_view.apply {
            setHasFixedSize(true)
            adapter =
                TrendingAdapter(
                    trendingTvList.sortedByDescending { it.voteAverage },
                    this@DiscoverFragment
                )
        }

        (activity as DashboardActivity).supportActionBar?.let {
            it.title = getString(R.string.title_discovery)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(airingTodayTv: ApiResponse<Tv>, trendingTV: ApiResponse<Tv>) =
            DiscoverFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(Constants.AIRING_TODAY_PARAM, airingTodayTv)
                        putParcelable(Constants.TRENDING_PARAM, trendingTV)
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
        val id = item.itemId
        if (id == R.id.action_search) {
            Toast.makeText(activity, "Clicked !", Toast.LENGTH_SHORT).show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}