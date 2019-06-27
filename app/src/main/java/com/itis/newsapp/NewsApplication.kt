package com.itis.newsapp

import android.content.Context
import com.itis.newsapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class NewsApplication : DaggerApplication() {

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