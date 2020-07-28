package com.arbelkilani.bingetv.presentation.ui.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view = activity?.findViewById<View>(R.id.action_show_more)
        when (item.itemId) {
            R.id.action_show_more -> {
                showPopupWindow(view)
            }
        }
        return super.onOptionsItemSelected(item)

    }

    private fun showPopupWindow(view: View?) {

        val axis = IntArray(2)
        val widthPixels = resources.displayMetrics.widthPixels

        val layout = layoutInflater.inflate(R.layout.layout_profile_popup_menu, null)
        layout?.apply {
            measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        }

        view?.apply {
            getLocationInWindow(axis)
        }

        val popupWindow = PopupWindow(activity)
        popupWindow.apply {
            setWindowLayoutMode(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            contentView = layout
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            showAtLocation(view, Gravity.NO_GRAVITY, widthPixels - view?.width!!, 0)
        }
    }

    companion object {
        private const val TAG = "ProfileFragment"
    }
}