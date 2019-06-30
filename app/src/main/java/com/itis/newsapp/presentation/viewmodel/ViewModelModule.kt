package com.itis.newsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.newsapp.presentation.ui.chosen.ChosenNewsViewModel
import com.itis.newsapp.presentation.ui.news.item.ArticleSharedViewModel
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.presentation.ui.news.list.NewsListViewModel
import com.itis.newsapp.presentation.ui.source.SourcesListViewModel
import com.itis.newsapp.presentation.base.viewmodel.IndicatorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SourcesListViewModel::class)
    abstract fun bindSourceListViewModel(sourceListViewModel: SourcesListViewModel): ViewModel

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
    @ViewModelKey(ArticleSharedViewModel::class)
    abstract fun bindArticleSharedViewModel(newsItemViewModel: ArticleSharedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChosenNewsViewModel::class)
    abstract fun bindChosenNewsViewModel(newsItemViewModel: ChosenNewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IndicatorViewModel::class)
    abstract fun bindIndicatorViewModel(newsItemViewModel: IndicatorViewModel): ViewModel
}