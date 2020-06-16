package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.listeners.OnTvClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending_view.view.*

class TrendingAdapter(
    private val trendingTv: List<Tv>,
    private val onTvClickListener: OnTvClickListener
) :
    RecyclerView.Adapter<TrendingAdapter.TrendingHolder>() {

    private val TAG = TrendingAdapter::class.java.simpleName

    class TrendingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val TAG = DiscoverAdapter::class.java.simpleName

        fun bind(
            tv: Tv,
            onTvClickListener: OnTvClickListener
        ) {
            Picasso.get()
                .load(tv.posterPath)
                .fit()
                .centerCrop()
                .into(itemView.iv_item_trending)

            itemView.tv_trending_name.text = tv.name
            itemView.setOnClickListener {
                onTvClickListener.onTvItemClicked(tv)
            }
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
        holder.bind(trendingTv[position], onTvClickListener)
    }
}