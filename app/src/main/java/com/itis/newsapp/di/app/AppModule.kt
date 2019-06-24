package com.itis.newsapp.di.app

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

@Module()
class AppModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}