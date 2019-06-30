package com.itis.newsapp.presentation.base.databinding.converters

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.itis.newsapp.R
import com.itis.newsapp.util.Const
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .error(com.itis.newsapp.R.drawable.newspaper)
        .into(view, object: Callback {
            override fun onSuccess() {
                view.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
            }
        })
}

@BindingAdapter("android:text")
fun placeHolderEmptyText(tv: TextView, text: String?) {
    if(TextUtils.isEmpty(text)) {
        tv.text = tv.context.getString(R.string.no_information)
    } else {
        tv.text = text
    }
}

@BindingConversion
fun convertDateToString(date: Date?): String {
    val format = SimpleDateFormat(Const.TIME_VIEW_FORMAT, Locale.getDefault())
    val dateStr = date?.let { format.format(it) } ?: ""
    return dateStr
}

