package com.example.android.newsApp.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.newsApp.databinding.ArticleItemBinding
import com.example.android.newsApp.network.Article
import kotlinx.android.synthetic.main.article_item.view.*

/**
 * The RecyclerView adapter for displaying all articles.
 * Uses DiffUtil to find the differences. Has a click listener for a parameter.
 */

class RVAdapter(val onClickListener: RVAdapter.OnClickListener) :
    ListAdapter<Article, RVAdapter.NewsViewHolder>(DiffCallback) {
    /**
     * View holder gets [Article] information thanks to binding var with ArticleItem.
     */
    class NewsViewHolder(private var binding: ArticleItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.article = article
            // Allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows RecyclerView to calculate differences when list has changed.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsViewHolder {
        return NewsViewHolder(ArticleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = getItem(position)

        // Set click listener
        holder.itemView.setOnClickListener {
            onClickListener.onClick(news)
        }

        holder.bind(news)
        holder.itemView.apply {
            titleTV.text = news.title
            timeTV.text = news.publishedAt
            sourceTV.text = news.author
        }
    }

    /**
     * Click listener that manages clicks on an article.
     */
    class OnClickListener(val clickListener: (article:Article) -> Unit) {
        fun onClick(article:Article) = clickListener(article)
    }
}
