package com.arbelkilani.bingetv.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentProfileBinding
import com.arbelkilani.bingetv.presentation.viewmodel.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.statistics.observe(viewLifecycleOwner, Observer { statisticsEntity ->
            binding.statisticsEntity = statisticsEntity
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer { genres ->
            binding.genres = genres
        })

        return binding.root
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            viewModel.refresh()
        }
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}