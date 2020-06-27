package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.arbelkilani.bingetv.presentation.listeners.OnTvShowClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_trending_view.view.*

class TrendingAdapter(
    private val onTvShowClickListener: OnTvShowClickListener
) :
    RecyclerView.Adapter<TrendingAdapter.TrendingHolder>() {

    private val TAG = TrendingAdapter::class.java.simpleName

    private var tvList = listOf<Tv>()

    class TrendingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val TAG = DiscoverAdapter::class.java.simpleName

        fun bind(
            tv: Tv,
            onTvShowClickListener: OnTvShowClickListener
        ) {
            Picasso.get()
                .load(tv.posterPath)
                .fit()
                .centerCrop()
                .into(itemView.iv_item_trending)

            itemView.tv_trending_name.text = tv.name
            itemView.setOnClickListener {
                onTvShowClickListener.onTvItemClicked(tv)
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
        return tvList.size
    }

    override fun onBindViewHolder(holder: TrendingHolder, position: Int) {
        holder.bind(tvList[position], onTvShowClickListener)
    }

    fun setData(it: List<Tv>?) {
        it?.let {
            tvList = it
            notifyDataSetChanged()
        }
    }
}