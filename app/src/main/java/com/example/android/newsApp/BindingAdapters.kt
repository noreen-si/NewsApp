package com.example.android.newsApp

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.newsApp.network.Article
import com.example.android.newsApp.overview.NewsStatus
import com.example.android.newsApp.overview.RVAdapter

/**
 * Only show RecyclerView when data is not null.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Article>?) {
    val adapter = recyclerView.adapter as RVAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

/**
 * This binding adapter displays the [NewsStatus] of the network request in an image view.
 */
@BindingAdapter("newsApiStatus")
fun bindStatus(statusImageView: ImageView, status: NewsStatus?) {
    when (status) {
        NewsStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        NewsStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        NewsStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {
            statusImageView.visibility = View.GONE
        }
    }
}
