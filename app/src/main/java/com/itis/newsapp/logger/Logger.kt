package com.itis.newsapp.logger

import android.util.Log
import com.itis.newsapp.BuildConfig

object Logger {

    fun v(tag: String, message: String) {
        if (BuildConfig.IS_USE_LOG)
            Log.v(tag, message)
    }

    fun d(tag: String, message: String) {
        if (BuildConfig.IS_USE_LOG)
            Log.d(tag, message)
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.IS_USE_LOG)
            Log.e(tag, message)
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.IS_USE_LOG)
            Log.i(tag, message)
    }

    fun w(tag: String, message: String) {
        if (BuildConfig.IS_USE_LOG)
            Log.w(tag, message)
    }

}