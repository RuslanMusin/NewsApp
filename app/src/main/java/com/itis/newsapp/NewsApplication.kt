package com.itis.newsapp

import android.content.Context
import com.itis.newsapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class NewsApplication : DaggerApplication() {

    companion object {
        private const val API_KEY_METRICA = "d1c7a5e0-5866-480b-9387-d968731cc632"
        private val NETWORK_THREAD_POOL = Executors.newFixedThreadPool(2)
        val NETWORK_SCHEDULER = Schedulers.from(NETWORK_THREAD_POOL)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }

    }

}