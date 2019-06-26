package com.itis.newsapp.util.converters

import com.squareup.picasso.Picasso
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import android.R.attr.name
import androidx.databinding.BindingConversion
import com.itis.newsapp.R
import com.itis.newsapp.util.Const.TIME_STR_FORMAT
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:url")
fun loadImage(view: ImageView, url: String) {
    Picasso.get().load(url).error(R.drawable.ic_info).into(view)
}

@BindingConversion
fun convertDateToString(date: Date): String {
    val format = SimpleDateFormat(TIME_STR_FORMAT, Locale.getDefault())
    return format.format(date)
}