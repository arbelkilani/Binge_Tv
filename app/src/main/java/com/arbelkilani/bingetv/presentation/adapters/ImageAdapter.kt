package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.image.Image
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(private val imagesList: List<Image>) :
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Image) {
            Picasso.get()
                .load(image.filePath)
                .fit()
                .centerCrop()
                .into(itemView.iv_season_poster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val inflatedView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.bind(imagesList[position % imagesList.size])
    }


}