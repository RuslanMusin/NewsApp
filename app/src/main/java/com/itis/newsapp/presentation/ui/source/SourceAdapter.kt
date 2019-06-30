package com.itis.newsapp.presentation.ui.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.newsapp.R
import com.itis.newsapp.presentation.model.SourceModel
import java.util.*

class SourceAdapter(
    private val clickCallback: SourcesFragment.SourceClickCallback
) : RecyclerView.Adapter<ProductViewHolder>() {

    internal var list: List<SourceModel>? = null

    init {
        setHasStableIds(true)
    }

    fun setSourceList(sourceList: List<SourceModel>) {
        if (list == null) {
            list = sourceList
            notifyItemRangeInserted(0, sourceList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return list!!.size
                }

                override fun getNewListSize(): Int {
                    return sourceList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return list!![oldItemPosition].id === sourceList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newItem = sourceList[newItemPosition]
                    val oldItem = list!![oldItemPosition]
                    return (newItem.id === oldItem.id
                            && Objects.equals(newItem.description, oldItem.description)
                            && Objects.equals(newItem.name, oldItem.name)
                            )
                }
            })
            list = sourceList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: com.itis.newsapp.databinding.ItemSourceBinding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.item_source,
                parent, false
            ) as com.itis.newsapp.databinding.ItemSourceBinding
        binding.setCallback(clickCallback)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.setSource(list!![position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (list == null) 0 else list!!.size
    }

    override fun getItemId(position: Int): Long {
        return list!![position].id.hashCode().toLong()
    }
}

class ProductViewHolder(val binding: com.itis.newsapp.databinding.ItemSourceBinding) : RecyclerView.ViewHolder(binding.getRoot())