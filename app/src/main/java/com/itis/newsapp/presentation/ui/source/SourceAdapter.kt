package com.itis.newsapp.presentation.ui.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.databinding.ProductItemBinding
import java.util.*

class SourceAdapter(
    private val mProductClickCallback: SourcesFragment.ProductClickCallback
) : RecyclerView.Adapter<ProductViewHolder>() {

    internal var mProductList: List<Source>? = null

    init {
        setHasStableIds(true)
    }

    fun setProductList(productList: List<Source>) {
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
                    return mProductList!![oldItemPosition].id === productList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newProduct = productList[newItemPosition]
                    val oldProduct = mProductList!![oldItemPosition]
                    return (newProduct.id === oldProduct.id
                            && Objects.equals(newProduct.description, oldProduct.description)
                            && Objects.equals(newProduct.name, oldProduct.name)
                            )
                }
            })
            mProductList = productList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding: com.itis.newsapp.databinding.ProductItemBinding = DataBindingUtil
            .inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context), R.layout.product_item,
                parent, false
            ) as ProductItemBinding
        binding.setCallback(mProductClickCallback)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.setProduct(mProductList!![position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mProductList == null) 0 else mProductList!!.size
    }

    override fun getItemId(position: Int): Long {
        return mProductList!![position].id.hashCode().toLong()
    }
}

class ProductViewHolder(val binding: com.itis.newsapp.databinding.ProductItemBinding) : RecyclerView.ViewHolder(binding.getRoot())