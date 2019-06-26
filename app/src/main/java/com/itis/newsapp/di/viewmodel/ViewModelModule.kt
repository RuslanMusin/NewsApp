package com.itis.newsapp.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.newsapp.presentation.ui.chosen.ChosenNewsViewModel
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.presentation.ui.news.list.NewsListViewModel
import com.itis.newsapp.presentation.ui.source.SourceListViewModel
import com.itis.newsapp.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SourceListViewModel::class)
    abstract fun bindSourceListViewModel(sourceListViewModel: SourceListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    abstract fun bindNewsListViewModel(newsListViewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsItemViewModel::class)
    abstract fun bindNewsItemViewModel(newsItemViewModel: NewsItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChosenNewsViewModel::class)
    abstract fun bindChosenNewsViewModel(newsItemViewModel: ChosenNewsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}