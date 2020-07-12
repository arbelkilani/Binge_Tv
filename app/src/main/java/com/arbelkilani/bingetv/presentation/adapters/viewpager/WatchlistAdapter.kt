package com.arbelkilani.bingetv.presentation.adapters.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.domain.entities.tv.TvShowEntity
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_watchlist.view.*


class WatchlistAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) : PagerAdapter() {

    private var tvShowList = mutableListOf<TvShowEntity>()

    companion object {
        private const val TAG = "WatchlistAdapter"
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return tvShowList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layout = LayoutInflater.from(container.context)
            .inflate(R.layout.item_watchlist, container, false)

        val tvShow = tvShowList[position]

        Picasso.get()
            .load(tvShow.poster)
            .fit().centerCrop()
            .into(layout.iv_poster)

        layout.main_container.setOnClickListener {
            onTvShowClickListener.onTvItemClicked(tvShow)
        }

        container.addView(layout)
        return layout
    }

    fun notifyDataSetChanged(it: List<TvShowEntity>?) {
        tvShowList.clear()
        tvShowList.addAll(it!!)
        notifyDataSetChanged()
    }
}