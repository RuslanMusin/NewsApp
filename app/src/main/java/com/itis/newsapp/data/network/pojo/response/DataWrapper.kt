package com.itis.newsapp.data.network.pojo.response

class DataWrapper<T> {
    var apiException: Exception? = null
    var data: T? = null
}