package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.source.SourcesFragment.Companion.SOURCE_ARG
import kotlinx.android.synthetic.main.fragment_sources.*
import javax.inject.Inject
import com.itis.newsapp.R
import com.itis.newsapp.presentation.ui.news.item.NewsItemFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel

class NewsFragment :
    BindingFragment<com.itis.newsapp.databinding.FragmentNewsBinding>()
{

    companion object {

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var newsViewModel: NewsListViewModel
    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.news)
        setNavigationIconVisibility(true)
        newsAdapter = NewsAdapter(mProductClickCallback);
        binding.newsList.setAdapter(newsAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
        newsItemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        arguments?.let {
            val sourceId = it.getString(SOURCE_ARG)
            sourceId?.let {
                newsViewModel.setNews(it)
                subscribeUi(newsViewModel.articles)
            }

        }
    }

    private fun subscribeUi(liveData: LiveData<List<Article>>) {
        liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
                    hideWaitProgressDialog()
                    loading_tv.visibility = View.GONE
                    newsAdapter.setNewsList(myProducts)
                } else {
                    showWaitProgressDialog(getString(R.string.loading))
                }
                binding.executePendingBindings()
            })
    }

    interface ProductClickCallback {
        fun onClick(article: Article)
    }


    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(article: Article) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${article.title}")
                val args = Bundle()
                args.putBoolean(NewsItemFragment.SHOW_ADD_ARG, true)
                newsItemViewModel.selectArticle(article)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_newsFragment_to_newsItemFragment, args) }
            }
        }
    }

}