package com.itis.newsapp.presentation.ui.news.item

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.itis.newsapp.R
import com.itis.newsapp.databinding.FragmentNewsItemBinding
import com.itis.newsapp.presentation.base.fragment.BindingFragment
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.model.common.Status
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import kotlinx.android.synthetic.main.fragment_news_item.*

class NewsItemFragment : BindingFragment<FragmentNewsItemBinding>() {

    companion object {
        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news_item

    private lateinit var statesViewModel: NewsItemViewModel
    private lateinit var articleSharedViewModel: ArticleSharedViewModel

    val safeArgs: NewsItemFragmentArgs by navArgs()

    lateinit var addBtn: MenuItem

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        setListeners()
        setHasOptionsMenu(safeArgs.showAddBtn)
        setViewModels()
        bindModel()
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.news_item)
        setNavigationIconVisibility(true)
    }

    private fun setListeners() {
        li_content.setOnClickListener { showContent() }
    }

    private fun setViewModels() {
        statesViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        statesViewModel.initState()
        articleSharedViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[ArticleSharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    private fun bindModel() {
        binding.itemId = getCurrentItemId()
        binding.model = articleSharedViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.add_menu, menu)
        addBtn = menu.findItem(R.id.add_article)
        observeViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_article -> addArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addArticle() {
        statesViewModel.setAddBtnClicked()
        articleSharedViewModel.article.value?.get(getCurrentItemId())?.let {
            statesViewModel.addArticle(it)
        }
    }

    private fun observeViewModel() {
        articleSharedViewModel.article.value?.get(getCurrentItemId())?.let {
            statesViewModel.checkArticleExists(it)
        }
        statesViewModel.response.observe(this) { res -> processResponse(res)}
        statesViewModel.isAddBtnClicked.observe(this) {
            if(it) {
                showInfoDialog(R.string.article_add_title, R.string.article_succes_added)
            }
        }
    }

    private fun processResponse(response: Response<Boolean>) {
        when (response.status) {
            Status.SUCCESS -> renderDataState(response.data)

            Status.ERROR -> renderErrorState(response.errorMessage)

            else -> {}
        }
    }

    private fun renderDataState(isAdded: Boolean?) {
        if (isAdded != null && isAdded) {
            addBtn.setVisible(false)
        } else {
            addBtn.setVisible(true)
        }
    }

    private fun renderErrorState(errorMessage: String?) {
        hideWaitProgressDialog()
        errorMessage?.let { showErrorDialog(it, true, R.string.error) }
    }

    fun showContent() {
        expandable_layout.toggle()
        if(expandable_layout.isExpanded) {
            iv_arrow.rotation = 180f
        } else {
            iv_arrow.rotation = 0f
        }
    }

}