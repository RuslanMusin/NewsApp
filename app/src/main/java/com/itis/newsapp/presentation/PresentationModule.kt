package com.itis.newsapp.presentation

import android.content.Context
import com.itis.newsapp.presentation.ui.RootBuilder
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessor
import com.itis.newsapp.presentation.util.exception.processor.ExceptionProcessorImpl
import com.itis.newsapp.presentation.viewmodel.ViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RootBuilder::class, ViewModelModule::class])
class PresentationModule {

    @Provides
    @Singleton
    fun provideExceptionProcessor(context: Context): ExceptionProcessor = ExceptionProcessorImpl(context)

}