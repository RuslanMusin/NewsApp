package com.itis.newsapp.presentation.util.exception.processor

import android.content.Context
import android.security.keystore.UserNotAuthenticatedException
import androidx.annotation.StringRes
import com.itis.newsapp.R
import com.itis.newsapp.data.network.exception.domain.DomainException
import com.itis.newsapp.domain.exceptions.NotInternetConnectionException
import com.itis.newsapp.domain.exceptions.ServerException
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor

class ExceptionProcessorImpl(private val context: Context) : ExceptionProcessor {

    override fun processException(t: Throwable): String {
        return when (t) {
            is NotInternetConnectionException -> getString(R.string.error_not_internet_connection)
            is ServerException -> getString(R.string.error_server)
            is DomainException -> getString(R.string.error_domain)
            else -> getString(R.string.error_unexpected)
        }
    }

    private fun getString(@StringRes text: Int): String {
        return context.getString(text)
    }

}