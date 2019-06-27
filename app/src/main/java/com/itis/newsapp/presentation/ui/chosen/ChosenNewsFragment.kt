package com.itis.newsapp.presentation.ui.chosen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.databinding.FragmentNewsBinding
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.presentation.ui.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import kotlinx.android.synthetic.main.fragment_sources.*
import javax.inject.Inject

class ChosenNewsFragment : BindingFragment<FragmentNewsBinding>() {

    companion object {

        fun getInstance() = ChosenNewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var mProductAdapter: NewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var chosenNewsViewModel: ChosenNewsViewModel
    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.chosen_news)
        setNavigationIconVisibility(false)
        mProductAdapter = NewsAdapter(mProductClickCallback);
        binding.newsList.setAdapter(mProductAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        chosenNewsViewModel = ViewModelProviders.of(this, viewModelFactory)[ChosenNewsViewModel::class.java]
        newsItemViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        subscribeUi(chosenNewsViewModel.articles)
    }

    private fun subscribeUi(liveData: LiveData<List<Article>>) {
        liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
                    loading_tv.visibility = View.GONE
                    mProductAdapter.setNewsList(myProducts)
                } else {
                }
                binding.executePendingBindings()
            })
    }

    private val mProductClickCallback = object : NewsFragment.ProductClickCallback {
        override fun onClick(article: Article) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${article.title}")
                val args = Bundle()
                newsItemViewModel.selectArticle(article)
                args.putBoolean(NewsItemFragment.SHOW_ADD_ARG, false)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_chosenNewsFragment_to_newsItemFragment, args) }
            }
        }
    }
}