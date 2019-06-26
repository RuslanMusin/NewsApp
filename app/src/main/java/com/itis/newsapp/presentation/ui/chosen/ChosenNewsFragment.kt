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
import com.itis.newsapp.presentation.base.navigation.BackBtnVisibilityListener
import com.itis.newsapp.presentation.ui.news.item.NewsItemFragment
import com.itis.newsapp.presentation.ui.news.list.NewsAdapter
import com.itis.newsapp.presentation.ui.news.list.NewsFragment
import kotlinx.android.synthetic.main.fragment_sources.*
import javax.inject.Inject

class ChosenNewsFragment : BindingFragment<FragmentNewsBinding>(), BackBtnVisibilityListener {

    companion object {

        fun getInstance() = ChosenNewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var mProductAdapter: NewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: ChosenNewsViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.chosen_news)
        mProductAdapter = NewsAdapter(mProductClickCallback);
        binding.newsList.setAdapter(mProductAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[ChosenNewsViewModel::class.java]
        subscribeUi(sourceListViewModel.articles)
    }

    private fun subscribeUi(liveData: LiveData<List<Article>>) {
        // Update the list when the data changes
        liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
//                    binding.setIsLoading(false)
                    loading_tv.visibility = View.GONE
                    mProductAdapter.setNewsList(myProducts)
                } else {
//                    binding.setIsLoading(true)
                }
                binding.executePendingBindings()
            })
    }

    private val mProductClickCallback = object : NewsFragment.ProductClickCallback {
        override fun onClick(product: Article) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${product.title}")
                val args = Bundle()
                args.putSerializable(NewsItemFragment.NEWS_ITEM_ARG, product)
                args.putBoolean(NewsItemFragment.SHOW_ADD_ARG, false)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_chosenNewsFragment_to_newsItemFragment, args) }
            }
        }
    }

    override fun setVisibility(isVisible: Boolean) {
        (activity as BackBtnVisibilityListener).setVisibility(false)
    }
}