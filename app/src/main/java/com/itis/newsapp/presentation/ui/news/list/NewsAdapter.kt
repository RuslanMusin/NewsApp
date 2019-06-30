package com.itis.newsapp.presentation.ui.news.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.newsapp.R
import com.itis.newsapp.databinding.ItemNewsBinding
import com.itis.newsapp.presentation.model.ArticleModel
import java.util.*

class NewsAdapter(
    private val itemClickListener: NewsItemClickListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    internal var newsList: List<ArticleModel>? = null

    init {
        setHasStableIds(true)
    }

    fun setNewsList(list: List<ArticleModel>) {
        if (newsList == null) {
            newsList = list
            notifyItemRangeInserted(0, list.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return newsList!!.size
                }

                override fun getNewListSize(): Int {
                    return list.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return newsList!![oldItemPosition].url === list[newItemPosition].url
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = list[newItemPosition]
                    val oldItem = newsList!![oldItemPosition]
                    return (newItem.url === oldItem.url
                            && Objects.equals(newItem.author, oldItem.author)
                            && Objects.equals(newItem.description, oldItem.description)
                            && Objects.equals(newItem.title, oldItem.title)
                            && Objects.equals(newItem.urlToImage, oldItem.urlToImage)
                            && Objects.equals(newItem.content, oldItem.content)
                            && Objects.equals(newItem.source, oldItem.source)
                            && Objects.equals(newItem.publishedAt, oldItem.publishedAt)
                            )
                }
            })
            newsList = list
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding: com.itis.newsapp.databinding.ItemNewsBinding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_news,
                parent, false
            ) as ItemNewsBinding
        binding.setCallback(itemClickListener)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.article = newsList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (newsList == null) 0 else newsList!!.size
    }

    override fun getItemId(position: Int): Long {
        return newsList!![position].url.hashCode().toLong()
    }
}

class NewsViewHolder(val binding: com.itis.newsapp.databinding.ItemNewsBinding) : RecyclerView.ViewHolder(binding.getRoot())