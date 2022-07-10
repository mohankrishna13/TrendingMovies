package com.example.trendingmovies.util
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
@BindingAdapter("load")
fun loadImage(view: ImageView, url: String) {
    if (!url.isNullOrEmpty()) {
        url?.let {
            Glide.with(view.context).load(it).into(view)
        }
    }

}