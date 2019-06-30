package com.itis.newsapp.presentation.ui.fragments.news.list.all

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.itis.newsapp.R
import com.itis.newsapp.databinding.FragmentNewsBinding
import com.itis.newsapp.presentation.base.fragment.BindingFragment
import com.itis.newsapp.presentation.model.ArticleModel
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.model.common.Status
import com.itis.newsapp.presentation.ui.fragments.news.ArticleSharedViewModel
import com.itis.newsapp.presentation.ui.fragments.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.fragments.news.list.NewsItemClickListener
import com.itis.newsapp.util.observeOnlyOnce

class NewsFragment :
    BindingFragment<FragmentNewsBinding>(),
    NewsItemClickListener
{

    companion object {

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var newsAdapter: NewsAdapter

    val safeArgs: NewsFragmentArgs by navArgs()

    private lateinit var newsViewModel: NewsListViewModel
    private lateinit var articleSharedViewModel: ArticleSharedViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        setViewModels()
        observeViewModel()
        bindModel()
        newsViewModel.loadNews(safeArgs.sourceId)
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.news)
        setNavigationIconVisibility(true)
    }

    private fun setViewModels() {
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
        newsViewModel.initState()
        articleSharedViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[ArticleSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    private fun bindModel() {
        binding.indicatorModel = indicatorViewModel
        newsAdapter = NewsAdapter(this)
        binding.newsList.setAdapter(newsAdapter)
    }

    private fun observeViewModel() {
        newsViewModel.response.observe(this) { res -> processResponse(res) }
        newsViewModel.isDisconnected.observe(this) {
            if(it) {
                showDisconnectView()
            }
        }
    }


    private fun processResponse(response: Response<List<ArticleModel>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data)

            Status.ERROR -> renderErrorState(response.errorMessage)
        }
    }

    private fun renderLoadingState() {
        showWaitProgressDialog()
    }

    private fun renderDataState(sources: List<ArticleModel>?) {
        hideWaitProgressDialog()
        sources?.let { newsAdapter.setNewsList(it) }
    }

    private fun renderErrorState(errorMessage: String?) {
        hideWaitProgressDialog()
        errorMessage?.let { showErrorDialog(it, true, R.string.error) }
    }

    override fun onRetry() {
        newsViewModel.response.observeOnlyOnce(this, Observer {
            if (it.status == Status.ERROR) {
                newsViewModel.loadNews(safeArgs.sourceId)
            }
        })
    }

    override fun onClick(article: ArticleModel) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            articleSharedViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_newsItemFragment) }
        }
    }

}