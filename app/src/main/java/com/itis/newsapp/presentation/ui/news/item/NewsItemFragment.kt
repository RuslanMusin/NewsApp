package com.itis.newsapp.presentation.ui.news.item

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.databinding.FragmentNewsBinding
import com.itis.newsapp.presentation.base.BaseFragment
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import com.itis.newsapp.presentation.ui.news.list.NewsListViewModel
import com.itis.newsapp.presentation.ui.source.SourcesFragment
import kotlinx.android.synthetic.main.fragment_sources.*
import kotlinx.android.synthetic.main.fragment_news_item.*
import javax.inject.Inject

class NewsItemFragment : BindingFragment<com.itis.newsapp.databinding.FragmentNewsItemBinding>() {

    companion object {
        const val NEWS_ITEM_ARG: String = "news_item_arg"
        const val SHOW_ADD_ARG: String = "show_add_arg"

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news_item

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: NewsItemViewModel

    lateinit var source: Article

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            source = it.getSerializable(NewsItemFragment.NEWS_ITEM_ARG) as Article
            sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
            sourceListViewModel.setNews(source)
            subscribeUi(sourceListViewModel.article)
        }
    }

    private fun subscribeUi(liveData: LiveData<Article>) {
        // Update the list when the data changes
        liveData.observe(this,
            Observer<Article> { myProducts ->
                if (myProducts != null) {
//                    binding.setIsLoading(false)
                    binding.article = myProducts
                } else {
//                    binding.setIsLoading(true)
                }
                binding.executePendingBindings()
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val showAddBtn = it.getBoolean(SHOW_ADD_ARG)
            if(showAddBtn) {
                setHasOptionsMenu(true);
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_article -> addArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addArticle() {
        sourceListViewModel.addArticle()
    }

}