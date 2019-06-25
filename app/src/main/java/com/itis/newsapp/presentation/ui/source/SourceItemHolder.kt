package com.itis.newsapp.presentation.ui.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.source.Source
import kotlinx.android.synthetic.main.item_source.view.*

class SourceItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Source) {
        itemView.tv_name.text = item.name
        itemView.tv_description.text = item.description
    }

    companion object {

        fun create(parent: ViewGroup): SourceItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false);
            val holder = SourceItemHolder(view)
            return holder
        }
    }
}