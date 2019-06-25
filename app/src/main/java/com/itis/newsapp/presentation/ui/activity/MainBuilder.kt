package com.itis.newsapp.presentation.ui.activity

import com.itis.newsapp.presentation.ui.chosen.ChosenNewsFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemFragment
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import com.itis.newsapp.presentation.ui.source.SourcesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuilder {

    @ContributesAndroidInjector()
    abstract fun buildSourceFragment(): SourcesFragment

    @ContributesAndroidInjector()
    abstract fun buildNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    abstract fun buildNewsItemFragment(): NewsItemFragment

    @ContributesAndroidInjector()
    abstract fun buildChosenNewsFragment(): ChosenNewsFragment

}