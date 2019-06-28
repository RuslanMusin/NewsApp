package com.itis.newsapp.presentation.ui.chosen

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.databinding.FragmentNewsBinding
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.item.NewsItemViewModel
import com.itis.newsapp.presentation.ui.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.news.list.NewsItemClickListener
import javax.inject.Inject

class ChosenNewsFragment :
    BindingFragment<FragmentNewsBinding>(),
    NewsItemClickListener
{

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
        mProductAdapter = NewsAdapter(this);
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
        showWaitProgressDialog()
        liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
                    hideWaitProgressDialog()
                    mProductAdapter.setNewsList(myProducts)
                } else {
                    showWaitProgressDialog()
                }
                binding.executePendingBindings()
            })
    }

    override fun onClick(article: Article) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Log.d("TAG", "clicked ${article.title}")
            newsItemViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_to_newsItemFragment) }
        }
    }

}