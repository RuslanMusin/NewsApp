package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.news.Article
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.ui.source.SourcesFragment.Companion.SOURCE_ARG
import kotlinx.android.synthetic.main.data_fragment.*
import javax.inject.Inject

class NewsFragment : BindingFragment<com.itis.newsapp.databinding.FragmentNewsBinding>() {

    companion object {

        fun getInstance() = NewsFragment()
    }

    override val layout: Int = R.layout.fragment_news

    lateinit var mProductAdapter: NewsAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: NewsListViewModel

    lateinit var source: Source

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        mProductAdapter = NewsAdapter(mProductClickCallback);
        binding.productsList.setAdapter(mProductAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            source = it.getSerializable(SOURCE_ARG) as Source
            sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[NewsListViewModel::class.java]
            sourceListViewModel.setNews(source)
            subscribeUi(sourceListViewModel.articles)
        }
    }

    private fun subscribeUi(liveData: LiveData<List<Article>>) {
        // Update the list when the data changes
        liveData.observe(this,
            Observer<List<Article>> { myProducts ->
                if (myProducts != null) {
//                    binding.setIsLoading(false)
                    loading_tv.visibility = View.GONE
                    mProductAdapter.setProductList(myProducts)
                } else {
//                    binding.setIsLoading(true)
                }
                binding.executePendingBindings()
            })
    }

    interface ProductClickCallback {
        fun onClick(product: Article)
    }


    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(product: Article) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${product.title}")
               /* val args = Bundle()
                args.putSerializable(SOURCE, product)
                Navigation.findNavController(btn_enter).navigate(R.id.action_sourcesFragment_to_newsFragment, args)*/
            }
        }
    }
}