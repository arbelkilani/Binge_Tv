package com.arbelkilani.bingetv.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.tv.Tv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dicover_view.view.*

class DiscoverAdapter(private val tvList: List<Tv>) :
    RecyclerView.Adapter<DiscoverAdapter.DiscoverHolder>() {

    private val TAG = DiscoverAdapter::class.java.simpleName

    class DiscoverHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val TAG = DiscoverAdapter::class.java.simpleName

        fun bind(tv: Tv) {
            Picasso.get()
                .load(tv.getBackdropPath())
                .fit()
                .centerCrop()
                .error(R.drawable.ic_foreground)
                .placeholder(R.mipmap.ic_launcher_foreground)
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
        return tvList.size
    }

    override fun onBindViewHolder(holder: DiscoverHolder, position: Int) {
        holder.bind(tvList[position])
    }
}