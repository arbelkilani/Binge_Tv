package com.arbelkilani.bingetv.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.base.ApiResponse
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.adapters.DiscoverAdapter
import kotlinx.android.synthetic.main.fragment_discover.*

private const val ARG_PARAM1 = "param1"

class DiscoverFragment : Fragment() {

    private val TAG = DiscoverFragment::class.java.simpleName

    private lateinit var param1: ApiResponse<Tv>
    private lateinit var airingTodayList: List<Tv>

    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)!!
            airingTodayList = param1.results
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
        for (tv in airingTodayList) {
            Log.i(TAG, "title : ${tv.name}\n")
        }
    }

    private fun initViews() {
        linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = DiscoverAdapter(airingTodayList)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: ApiResponse<Tv>) =
            DiscoverFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}