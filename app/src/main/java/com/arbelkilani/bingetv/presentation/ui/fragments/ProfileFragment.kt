package com.arbelkilani.bingetv.presentation.ui.fragments

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.FragmentProfileBinding
import com.arbelkilani.bingetv.databinding.LayoutProfilePopupMenuBinding
import com.arbelkilani.bingetv.presentation.listeners.OnProfilePopupClicked
import com.arbelkilani.bingetv.presentation.viewmodel.profile.ProfileViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class ProfileFragment : Fragment(), OnProfilePopupClicked {

    companion object {
        private const val TAG = "ProfileFragment"
    }

    private val viewModel: ProfileViewModel by viewModel()
    private val preferences: SharedPreferences by inject(named("settingsPrefs"))

    private lateinit var binding: FragmentProfileBinding
    private lateinit var popupWindowBinding: LayoutProfilePopupMenuBinding

    private var popupWindow: PopupWindow? = null

    private val googleSignIn =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                viewModel.getSignedInAccountFromIntent(result.data)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.onCreate(requireContext())
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

        initPopupWindowBinding()

        viewModel.statistics.observe(viewLifecycleOwner, Observer { statisticsEntity ->
            binding.statisticsEntity = statisticsEntity
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer { genres ->
            binding.genres = genres
        })

        viewModel.firebaseUser.observe(viewLifecycleOwner, Observer {
            popupWindowBinding.user = it
            binding.user = it
            popupWindow?.dismiss()
        })

        return binding.root
    }

    private fun initPopupWindowBinding() {
        popupWindowBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.layout_profile_popup_menu,
            null,
            false
        )
        popupWindowBinding.viewModel = viewModel
        popupWindowBinding.onProfilePopupClicked = this
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

        popupWindowBinding.root.apply {
            measure(View.MeasureSpec.EXACTLY, View.MeasureSpec.EXACTLY)
        }

        view?.apply {
            getLocationInWindow(axis)
        }

        popupWindow = PopupWindow(activity)
        popupWindow?.apply {
            setWindowLayoutMode(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            contentView = popupWindowBinding.root
            isOutsideTouchable = true
            isFocusable = true
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            showAtLocation(view, Gravity.NO_GRAVITY, widthPixels - view?.width!!, 0)
        }
    }

    override fun signIn() {
        googleSignIn.launch(viewModel.signInIntent.value)
    }

    override fun signOut() {
        viewModel.signOut()
    }

    override fun synchronise() {
        viewModel.refresh()
        popupWindow?.dismiss()
        preferences.edit {
            putBoolean("SYNC", true)
        }
    }
}