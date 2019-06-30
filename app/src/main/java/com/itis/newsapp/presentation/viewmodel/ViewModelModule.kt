package com.itis.newsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.newsapp.presentation.ui.fragments.news.list.chosen.ChosenNewsViewModel
import com.itis.newsapp.presentation.ui.fragments.news.ArticleSharedViewModel
import com.itis.newsapp.presentation.ui.fragments.news.item.NewsItemViewModel
import com.itis.newsapp.presentation.ui.fragments.news.list.all.NewsListViewModel
import com.itis.newsapp.presentation.ui.fragments.source.SourcesListViewModel
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