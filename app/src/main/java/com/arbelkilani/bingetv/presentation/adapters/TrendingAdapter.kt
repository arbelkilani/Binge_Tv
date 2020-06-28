package com.arbelkilani.bingetv.presentation.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dicover_view.view.*

class TrendingAdapter(private val tvList: List<Tv>) : PagerAdapter() {
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

        Picasso.get()
            .load(tvList[virtualPosition].backdropPath)
            .fit().centerCrop(Gravity.CENTER)
            .error(R.mipmap.ic_launcher_round)
            .into(layout.iv_item_discover)
        layout.tv_title.text = tvList[virtualPosition].name

        container.addView(layout)
        return layout
    }


}
/*class DiscoverAdapter(private val tvList: List<Tv>) :
    RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>() {

    private val TAG = DiscoverAdapter::class.java.simpleName

    class DiscoverHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val TAG = DiscoverAdapter::class.java.simpleName

        fun bind(tv: Tv) {
            Picasso.get()
                .load(tv.getBackdropPath())
                .fit()
                .centerCrop()
                .into(itemView.iv_item_discover)
        }

        override fun onClick(v: View?) {
            Log.i(TAG, "item clicked")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiscoverHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_dicover_view, parent, false)
        return DiscoverHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) {
        holder.bind(tvList[position % tvList.size])
    }
}*/