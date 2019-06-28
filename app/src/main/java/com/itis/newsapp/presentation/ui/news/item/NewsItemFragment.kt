package com.itis.newsapp.presentation.ui.news.item

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
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

    val safeArgs: NewsItemFragmentArgs by navArgs()

    var isAddedClicked: Boolean = false
    lateinit var addBtn: MenuItem

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.news_item)
        setNavigationIconVisibility(true)
        li_content.setOnClickListener { showContent() }
        setHasOptionsMenu(safeArgs.showAddBtn)
        tv_url.movementMethod = LinkMovementMethod.getInstance()
        articleViewModel = activity?.run {
            ViewModelProviders.of(this, viewModelFactory)[NewsItemViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.itemId = getCurrentItemId()
        binding.model = articleViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.add_menu, menu)
        addBtn = menu.findItem(R.id.add_article)
        subscribeToUi()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_article -> addArticle()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun subscribeToUi() {
        articleViewModel.checkArticleExists(getCurrentItemId())
        articleViewModel.isAdded.observe(this,
            Observer<Boolean> { isAdded ->
                if (isAdded != null && isAdded) {
                    addBtn.setVisible(false)
                    if(isAddedClicked) {
                        isAddedClicked = false
                        showInfoDialog(R.string.article_add_title, R.string.article_succes_added)
                    }
                } else {
                    addBtn.setVisible(true)
                }
                binding.executePendingBindings()
            })
    }

    private fun addArticle() {
        isAddedClicked = true
        articleViewModel.addArticle(getCurrentItemId())
    }

    fun showContent() {
        expandable_layout.toggle()
        if(expandable_layout.isExpanded) {
            iv_arrow.rotation = 180f
        } else {
            iv_arrow.rotation = 0f
        }
    }

    interface ProductClickCallback {
        fun onClick(article: Article)
    }

}