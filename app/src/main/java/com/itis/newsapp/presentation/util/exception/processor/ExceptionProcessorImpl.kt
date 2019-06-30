package com.itis.newsapp.presentation.util.exception.processor

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.itis.newsapp.R
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.exception.domain.DomainException

class ExceptionProcessorImpl(private val context: Context) : ExceptionProcessor {

    override fun processException(t: Throwable): String {
        return when (t) {
            is NoInternetConnectionException -> getString(R.string.error_not_internet_connection)
            is DomainException -> getString(R.string.error_domain)
            else -> getString(R.string.error_unexpected)
        }
    }

    private fun getString(@StringRes text: Int): String {
        return context.getString(text)
    }

}