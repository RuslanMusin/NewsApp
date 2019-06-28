package com.itis.newsapp.data.network.callback

import androidx.lifecycle.Observer
import com.itis.newsapp.data.network.pojo.response.DataWrapper

open class ApiObserver<T>(private val changeListener: ChangeListener<T>) : Observer<DataWrapper<T>> {

    override fun onChanged(dataWrapper: DataWrapper<T>?) {
        if (dataWrapper != null) {
            if (dataWrapper.apiException != null) {
                changeListener.onException(dataWrapper.apiException)
            } else {
                changeListener.onSuccess(dataWrapper.data)
            }
            return
        }
        changeListener.onDataLoad()
    }

    interface ChangeListener<T> {
        fun onSuccess(dataWrapper: T?)
        fun onException(exception: Exception?)
        fun onDataLoad()
    }
}