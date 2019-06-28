package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.itis.newsapp.R
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.data.network.callback.ApiObserver
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newsViewModel: NewsListViewModel
    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        bindModel()
    }

    override fun onResume() {
        super.onResume()
        newsViewModel.articles.observe(this, observer)
    }

    override fun onPause() {
        super.onPause()
        newsViewModel.articles.removeObserver(observer)
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.news)
        setNavigationIconVisibility(true)
    }

    private fun bindModel() {
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
        newsItemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        newsViewModel.articles.observe(this, observer)
        newsViewModel.setNews(safeArgs.sourceId)

        newsAdapter = NewsAdapter(this)
        binding.newsList.setAdapter(newsAdapter)
    }

    private val observer = ApiObserver<News>(object :
        ApiObserver.ChangeListener<News> {
        override fun onSuccess(dataWrapper: News?) {
            if (dataWrapper?.articles != null) {
                hideWaitProgressDialog()
                dataWrapper.articles.let { newsAdapter.setNewsList(it) }
            } else {
                showWaitProgressDialog(getString(R.string.loading))
            }
            binding.executePendingBindings()
        }

        override fun onException(exception: Exception?) {
            if (exception is NoInternetConnectionException) {
                exception.message?.let { showErrorDialog(it, true, R.string.disconnected) }
                showDisconnectView()
            }
        }

        override fun onDataLoad() {
            showWaitProgressDialog()
        }
    })

    override fun onRetry() {
        showWaitProgressDialog()
        newsViewModel.setNews(safeArgs.sourceId)
    }

    override fun onClick(article: Article) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            newsItemViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_newsItemFragment) }
        }
    }

}