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
    ListAdapter<Article, RVAdapter.MarsPropertyViewHolder>(DiffCallback) {
    /**
     * View holder gets [Article] information thanks to binding var with ArticleItem.
     */
    class MarsPropertyViewHolder(private var binding: ArticleItemBinding):
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
                                    viewType: Int): MarsPropertyViewHolder {
        return MarsPropertyViewHolder(ArticleItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)

        // Set click listener
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }

        holder.bind(marsProperty)
        holder.itemView.apply {
            titleTV.text = marsProperty.title
            timeTV.text = marsProperty.publishedAt
            sourceTV.text = marsProperty.author
        }
    }

    /**
     * Click listener that manages clicks on an article.
     */
    class OnClickListener(val clickListener: (article:Article) -> Unit) {
        fun onClick(article:Article) = clickListener(article)
    }
}
