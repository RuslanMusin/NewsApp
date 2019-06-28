package com.itis.newsapp.data.network.callback

import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.exception.TimeOutException
import com.itis.newsapp.data.network.exception.UnknownException
import com.itis.newsapp.data.network.exception.domain.DomainException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class ApiCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
        if (response.body() != null) {
            response.body()?.let {
                handleResponseData(it)
            }
        } else {
            handleError(response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is Exception) {
            handleException(t)
        } else {

        }
    }

    protected abstract fun handleResponseData(data: T)

    protected abstract fun handleError(response: retrofit2.Response<T>)

    protected abstract fun handleException(t: Exception)

    fun processApiThrowable(t: Throwable): Exception {
        return when(t) {
            is DomainException -> t
            is UnknownHostException -> NoInternetConnectionException("No internet")
            is SocketTimeoutException -> TimeOutException()
            else -> UnknownException()
        }
    }

    fun processApiThrowable(t: Response<T>): Exception {
        return when(t.code()) {
            500 -> DomainException()
            else -> UnknownException()
        }
    }
}