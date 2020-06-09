package com.arbelkilani.bingetv.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.adapters.DiscoverAdapter
import com.arbelkilani.bingetv.presentation.adapters.TrendingAdapter
import com.arbelkilani.bingetv.presentation.ui.view.CustomTransformer
import kotlinx.android.synthetic.main.fragment_discover.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DiscoverFragment : Fragment() {

    private val TAG = DiscoverFragment::class.java.simpleName

    private lateinit var param1: ApiResponse<Tv>
    private lateinit var param2: ApiResponse<Tv>

    private lateinit var airingTodayList: List<Tv>
    private lateinit var trendingTvList: List<Tv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)!!
            airingTodayList = param1.results

            param2 = it.getParcelable(ARG_PARAM2)!!
            trendingTvList = param2.results
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
            pageMargin = 30
            setPageTransformer(false, CustomTransformer())
        }

        recycler_view.apply {
            setHasFixedSize(true)
            adapter = TrendingAdapter(trendingTvList)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ApiResponse<Tv>, param2: ApiResponse<Tv>) =
            DiscoverFragment()
                .apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_PARAM1, param1)
                        putParcelable(ARG_PARAM2, param2)
                    }
                }
    }
}