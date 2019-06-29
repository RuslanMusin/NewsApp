package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.itis.newsapp.R
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.data.network.callback.ApiObserver
import com.itis.newsapp.presentation.ui.model.Response
import com.itis.newsapp.presentation.ui.model.Status
import com.itis.newsapp.util.observeOnlyOnce
import javax.inject.Inject

class NewsFragment :
    BindingFragment<com.itis.newsapp.databinding.FragmentNewsBinding>(),
    NewsItemClickListener
{

    companion object {

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var newsAdapter: NewsAdapter

    val safeArgs: NewsFragmentArgs by navArgs()

    /*@Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory*/
    private lateinit var newsViewModel: NewsListViewModel
    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
        newsItemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        observeViewModel()
        binding.indicatorModel = indicatorViewModel
        newsAdapter = NewsAdapter(this)
        binding.newsList.setAdapter(newsAdapter)
        newsViewModel.loadNews(safeArgs.sourceId)

    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.news)
        setNavigationIconVisibility(true)
    }

    private fun observeViewModel() {
        newsViewModel.response.observe(this) { res -> processResponse(res) }
        newsViewModel.isDisconnected.observe(this) {
            if(it) {
                showDisconnectView()
            }
        }
    }


    private fun processResponse(response: Response<List<Article>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data)

            Status.ERROR -> renderErrorState(response.errorMessage)
        }
    }

    private fun renderLoadingState() {
        showWaitProgressDialog()
    }

    private fun renderDataState(sources: List<Article>?) {
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

    override fun onClick(article: Article) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            newsItemViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_newsItemFragment) }
        }
    }

}