package com.arbelkilani.bingetv.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.adapters.DiscoverAdapter
import com.arbelkilani.bingetv.presentation.adapters.TrendingAdapter
import com.arbelkilani.bingetv.presentation.ui.view.CustomTransformer
import com.arbelkilani.bingetv.utils.Constants
import com.arbelkilani.bingetv.utils.dp2px
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {

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
        for (tv in trendingTvList) {
            Log.i(TAG, "title : ${tv.name}\n")
        }
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
            kotlin.run { Log.i(TAG, "width = $width") }
            adapter = TrendingAdapter(trendingTvList)
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
}