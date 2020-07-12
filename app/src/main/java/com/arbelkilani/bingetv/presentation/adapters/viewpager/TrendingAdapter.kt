package com.arbelkilani.bingetv.presentation.adapters.viewpager

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dicover_view.view.*

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
        val layout = LayoutInflater.from(container.context)
            .inflate(R.layout.item_dicover_view, container, false)

        val tvShow = tvList[virtualPosition]

        Picasso.get()
            .load(tvShow.backdrop)
            .placeholder(R.drawable.placeholder_large)
            .error(R.drawable.placeholder_large)
            .fit().centerCrop(Gravity.CENTER)
            .into(layout.iv_item_discover)

        layout.tv_title.text = tvShow.name

        layout.main_container.setOnClickListener {
            onTvShowClickListener.onTvItemClicked(tvShow)
        }

        container.addView(layout)
        return layout
    }
}