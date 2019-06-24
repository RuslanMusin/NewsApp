package com.itis.newsapp.presentation.ui

import com.itis.newsapp.presentation.ui.activity.MainActivity
import com.itis.newsapp.presentation.ui.activity.MainModule
import com.itis.newsapp.presentation.ui.activity.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RootBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    @MainScope
    abstract fun buildMainActivity(): MainActivity

}