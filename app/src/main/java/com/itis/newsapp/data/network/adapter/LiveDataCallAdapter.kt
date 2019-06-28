package com.itis.newsapp.data.network.adapter

import androidx.lifecycle.LiveData
import com.itis.newsapp.data.network.callback.ApiCallback
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<DataWrapper<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<DataWrapper<R>> {
        return object : LiveData<DataWrapper<R>>() {
            private var started = AtomicBoolean(false)
            private var dataWrapper = DataWrapper<R>()

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : ApiCallback<R>() {
                        override fun handleResponseData(data: R) {
                            dataWrapper.data = data
                            postValue(dataWrapper)
                        }

                        override fun handleError(response: retrofit2.Response<R>) {
                            dataWrapper.apiException = processApiThrowable(response)
                            postValue(dataWrapper)
                        }

                        override fun handleException(t: Exception) {
                            dataWrapper.apiException = processApiThrowable(t)
                            postValue(dataWrapper)
                        }
                    })
                }
            }
        }
    }
}
