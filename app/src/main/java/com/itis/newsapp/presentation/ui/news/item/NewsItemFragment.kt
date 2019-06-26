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
import com.itis.newsapp.presentation.base.navigation.BackBtnVisibilityListener
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import kotlinx.android.synthetic.main.fragment_news_item.*
import javax.inject.Inject

class NewsItemFragment : BindingFragment<com.itis.newsapp.databinding.FragmentNewsItemBinding>(),
    BackBtnVisibilityListener {

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
        setToolbarTitle(R.string.news_item)
        setVisibility(true)
        li_content.setOnClickListener { showContent() }
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
                    hideWaitProgressDialog()
                } else {
                    showWaitProgressDialog()
//                    binding.setIsLoading(true)
                }
                binding.executePendingBindings()
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            val showAddBtn = it.getBoolean(SHOW_ADD_ARG)
            Log.d("TAG", "showAddBtn = $showAddBtn")
            setHasOptionsMenu(showAddBtn)
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

    override fun setVisibility(isVisible: Boolean) {
        (activity as BackBtnVisibilityListener).setVisibility(isVisible)
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