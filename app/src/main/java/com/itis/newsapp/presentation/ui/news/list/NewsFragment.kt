package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.news.News
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.util.ApiObserver
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
        setToolbarTitle(R.string.news)
        setNavigationIconVisibility(true)
        newsAdapter = NewsAdapter(this);
        binding.newsList.setAdapter(newsAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
        newsItemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        subscribeUi(newsViewModel.articles)
        newsViewModel.setNews(safeArgs.sourceId)
    }

    private fun subscribeUi(liveData: LiveData<DataWrapper<News>>) {
        liveData.observe(this,
            ApiObserver<News>(object: ApiObserver.ChangeListener<News> {
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
                }

                override fun onDataLoad() {
                    showWaitProgressDialog()
                }

            })
        )
      /*  liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
                    hideWaitProgressDialog()
                    loading_tv.visibility = View.GONE
                    newsAdapter.setNewsList(myProducts)
                } else {
                    showWaitProgressDialog(getString(R.string.loading))
                }
                binding.executePendingBindings()
            })*/
    }

    override fun onClick(article: Article) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            newsItemViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_newsItemFragment) }
        }
    }

}