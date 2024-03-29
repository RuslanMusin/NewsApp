package com.itis.newsapp.di

import android.app.Application
import com.itis.newsapp.NewsApplication
import com.itis.newsapp.data.DataModule
import com.itis.newsapp.di.app.AppModule
import com.itis.newsapp.presentation.viewmodel.ViewModelModule
import com.itis.newsapp.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class,
        PresentationModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<NewsApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NewsApplication>() {

        @BindsInstance
        abstract fun application(application: Application): Builder

    }
}