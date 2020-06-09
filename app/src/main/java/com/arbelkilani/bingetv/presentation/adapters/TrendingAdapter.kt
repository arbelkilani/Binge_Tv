package com.arbelkilani.bingetv.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending_view.view.*

class TrendingAdapter(private val trendingTv: List<Tv>) :
    RecyclerView.Adapter<TrendingAdapter.TrendingHolder>() {

    private val TAG = TrendingAdapter::class.java.simpleName

    class TrendingHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val TAG = DiscoverAdapter::class.java.simpleName

        fun bind(tv: Tv) {
            Picasso.get()
                .load(tv.getPosterPath())
                .fit()
                .centerCrop()
                .into(itemView.iv_item_trending)

            itemView.tv_trending_name.text = tv.name
        }

        override fun onClick(v: View?) {
            Log.i(TAG, "item clicked")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_trending_view, parent, false)
        return TrendingHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return trendingTv.size
    }

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        holder.bind(trendingTv[position])
    }
}