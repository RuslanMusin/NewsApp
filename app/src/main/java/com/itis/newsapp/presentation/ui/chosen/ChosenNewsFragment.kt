package com.itis.newsapp.presentation.ui.chosen

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.databinding.FragmentNewsBinding
import com.itis.newsapp.presentation.base.fragment.BindingFragment
import com.itis.newsapp.presentation.model.ArticleModel
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.model.common.Status
import com.itis.newsapp.presentation.ui.news.item.ArticleSharedViewModel
import com.itis.newsapp.presentation.ui.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.news.list.NewsItemClickListener
import com.itis.newsapp.util.observeOnlyOnce

class ChosenNewsFragment :
    BindingFragment<FragmentNewsBinding>(),
    NewsItemClickListener
{

    companion object {

        fun getInstance() = ChosenNewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var chosenNewsAdapter: NewsAdapter

    private lateinit var chosenNewsViewModel: ChosenNewsViewModel
    private lateinit var articleSharedViewModel: ArticleSharedViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        setViewModels()
        observeViewModel()
        bindModel()
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.chosen_news)
        setNavigationIconVisibility(false)
    }

    private fun setViewModels() {
        chosenNewsViewModel = ViewModelProviders.of(this, viewModelFactory)[ChosenNewsViewModel::class.java]
        articleSharedViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[ArticleSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    private fun observeViewModel() {
        chosenNewsViewModel.response.observe(this) { res -> processResponse(res)}
        chosenNewsViewModel.isDisconnected.observe(this) {
            if(it) {
                showDisconnectView()
            }
        }
    }

    private fun bindModel() {
        binding.indicatorModel = indicatorViewModel
        chosenNewsAdapter = NewsAdapter(this)
        binding.newsList.setAdapter(chosenNewsAdapter)
        chosenNewsViewModel.loadChosenArticles()
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
        Log.d("TAG","update")
        hideWaitProgressDialog()
        sources?.let { chosenNewsAdapter.setNewsList(it) }
    }

    private fun renderErrorState(errorMessage: String?) {
        hideWaitProgressDialog()
        errorMessage?.let { showErrorDialog(it, true, R.string.error) }
    }

    override fun onRetry() {
        chosenNewsViewModel.response.observeOnlyOnce(this, Observer {
            if (it.status == Status.ERROR) {
                chosenNewsViewModel.loadChosenArticles()
            }
        })
    }

    override fun onClick(article: ArticleModel) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            articleSharedViewModel.selectArticle(article, getCurrentItemId())
            view?.let { Navigation.findNavController(it).navigate(R.id.action_to_newsItemFragment) }
        }
    }

}