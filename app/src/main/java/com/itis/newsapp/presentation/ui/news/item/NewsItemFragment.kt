package com.itis.newsapp.presentation.ui.news.item

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import kotlinx.android.synthetic.main.fragment_news_item.*
import javax.inject.Inject

class NewsItemFragment : BindingFragment<com.itis.newsapp.databinding.FragmentNewsItemBinding>() {

    companion object {
        const val SHOW_ADD_ARG: String = "show_add_arg"

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news_item

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleViewModel: NewsItemViewModel

    lateinit var source: Article

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.news_item)
        setNavigationIconVisibility(true)
        li_content.setOnClickListener { showContent() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articleViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.model = articleViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val showAddBtn = it.getBoolean(SHOW_ADD_ARG)
            setHasOptionsMenu(showAddBtn)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear();
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_article -> addArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addArticle() {
        articleViewModel.addArticle()
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