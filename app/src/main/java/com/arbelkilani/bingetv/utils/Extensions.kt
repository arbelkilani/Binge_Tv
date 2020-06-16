package com.arbelkilani.bingetv.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("android:src")
fun setImageUrl(view: ImageView, url: String?) {
    //TODO work on error and placeholder
    Picasso.get().load(url).fit().centerCrop().into(view)
}