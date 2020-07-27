package com.arbelkilani.bingetv.presentation.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ActivityListAllTvShowBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.adapters.SearchAdapter
import com.arbelkilani.bingetv.presentation.adapters.dataload.DataLoadStateAdapter
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.arbelkilani.bingetv.presentation.viewmodel.ListAllTvShowViewModel
import com.arbelkilani.bingetv.utils.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListAllTvShowActivity : AppCompatActivity(), OnTvShowClickListener {

    companion object {
        private const val TAG = "ListAllTvShowActivity"
    }

    private val getTvShowEntity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                result.data?.let {
                    val tvShow =
                        it.getParcelableExtra<TvShowEntity>(Constants.TV_SHOW_ENTITY_REQUEST)!!
                    val position = it.getIntExtra(Constants.TV_SHOW_ENTITY_POSITION_REQUEST, -1)
                    adapter.notifyTvShow(position, tvShow)
                }
            }
        }

    private val viewModel: ListAllTvShowViewModel by viewModel {
        parametersOf(
            intent.getStringExtra(Constants.SHOW_MORE_TAG)
        )
    }

    private lateinit var binding: ActivityListAllTvShowBinding

    private val adapter = SearchAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_all_tv_show)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.tvShowPagingData.observe(this, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })

        viewModel.toolbarTitle.observe(this, Observer {
            binding.toolbar.title = it
        })

        initToolbar()
        initAdapter()

    }

    private fun initAdapter() {
        binding.rv.adapter = adapter.withLoadStateFooter(
            footer = DataLoadStateAdapter { adapter.retry() })

        adapter.addLoadStateListener { loadState ->

            // Only show the list if refresh succeeds.
            binding.rv.isVisible = loadState.refresh is LoadState.NotLoading

            // Show loading spinner during initial load or refresh.
            binding.shimmer.isVisible = loadState.refresh is LoadState.Loading

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayShowTitleEnabled(false)
        }

        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    override fun onTvItemClicked(tvShowEntity: TvShowEntity, position: Int) {
        getTvShowEntity.launch(Intent(
            this, TvDetailsActivity::class.java
        ).apply {
            putExtra(Constants.TV_SHOW_ENTITY, tvShowEntity)
            putExtra(Constants.TV_SHOW_ENTITY_POSITION, position)
        })
    }
}