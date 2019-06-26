package com.itis.newsapp.presentation.ui.news.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.databinding.ItemNewsBinding
import com.itis.newsapp.presentation.ui.source.ProductViewHolder
import com.itis.newsapp.presentation.ui.source.SourcesFragment
import java.util.*

class NewsAdapter(
    private val mProductClickCallback: NewsFragment.ProductClickCallback
) : RecyclerView.Adapter<NewsViewHolder>() {

    internal var mProductList: List<Article>? = null

    init {
        setHasStableIds(true)
    }

    fun setNewsList(productList: List<Article>) {
        if (mProductList == null) {
            mProductList = productList
            notifyItemRangeInserted(0, productList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mProductList!!.size
                }

                override fun getNewListSize(): Int {
                    return productList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mProductList!![oldItemPosition].url === productList[newItemPosition].url
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newProduct = productList[newItemPosition]
                    val oldProduct = mProductList!![oldItemPosition]
                    return (newProduct.url === oldProduct.url
                            && Objects.equals(newProduct.author, oldProduct.author)
                            && Objects.equals(newProduct.description, oldProduct.description)
                            && Objects.equals(newProduct.title, oldProduct.title)
                            )
                }
            })
            mProductList = productList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding: com.itis.newsapp.databinding.ItemNewsBinding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_news,
                parent, false
            ) as ItemNewsBinding
        binding.setCallback(mProductClickCallback)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.article = mProductList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mProductList == null) 0 else mProductList!!.size
    }

    override fun getItemId(position: Int): Long {
        return mProductList!![position].url.hashCode().toLong()
    }
}

class NewsViewHolder(val binding: com.itis.newsapp.databinding.ItemNewsBinding) : RecyclerView.ViewHolder(binding.getRoot())