package com.itis.newsapp.presentation.util.exception.processor

interface ExceptionProcessor {
    fun processException(t: Throwable): String
}