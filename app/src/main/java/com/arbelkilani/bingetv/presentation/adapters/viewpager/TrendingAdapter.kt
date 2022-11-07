package com.arbelkilani.bingetv.presentation.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.databinding.ItemDicoverViewBinding
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener

class TrendingAdapter(
    private val tvList: List<TvShowEntity>,
    private val onTvShowClickListener: OnTvShowClickListener
) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    private fun getRealCount(): Int {
        return tvList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val virtualPosition = position % getRealCount()

        val binding = DataBindingUtil.inflate<ItemDicoverViewBinding>(
            LayoutInflater.from(container.context), R.layout.item_dicover_view, container, false
        )
        val tvShow = tvList[virtualPosition]
        binding.tvShowEntity = tvShow
        binding.tvShowListener = onTvShowClickListener

        container.addView(binding.root)
        return binding.root
    }
}