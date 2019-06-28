package com.itis.newsapp.util.converters

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.itis.newsapp.util.Const
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(com.itis.newsapp.R.drawable.ic_info)
        .error(com.itis.newsapp.R.drawable.ic_info)
        .into(view, object: Callback {
            override fun onSuccess() {
                view.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
            }
        })
}

@BindingConversion
fun convertDateToString(date: Date?): String {
    val format = SimpleDateFormat(Const.TIME_VIEW_FORMAT, Locale.getDefault())
    val dateStr = format.format(date)
    Log.d("TAG", "date = $dateStr")
    return dateStr
}

