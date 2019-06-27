package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.presentation.base.BindingFragment
import kotlinx.android.synthetic.main.fragment_sources.*
import javax.inject.Inject

class SourcesFragment : BindingFragment<com.itis.newsapp.databinding.FragmentSourcesBinding>() {

    companion object {

        const val SOURCE_ARG: String = "source_arg"

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = R.layout.fragment_sources

    lateinit var sourceAdapter: SourceAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: SourcesListViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarTitle(R.string.sources)
        setNavigationIconVisibility(false)
        sourceAdapter = SourceAdapter(mProductClickCallback);
        binding.sourceList.setAdapter(sourceAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[SourcesListViewModel::class.java]
        subscribeUi(sourceListViewModel.sources)
    }

    private fun subscribeUi(liveData: LiveData<List<Source>?>) {
        liveData.observe(this,
            Observer { sources ->
                if (sources != null) {
                    hideWaitProgressDialog()
                    loading_tv.visibility = View.GONE
                    sourceAdapter.setSourceList(sources)
                } else {
                    showWaitProgressDialog(getString(R.string.loading))
                }
                binding.executePendingBindings()
            })
    }

    interface ProductClickCallback {
        fun onClick(product: Source)
    }

    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(product: Source) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${product.name}")
                val args = Bundle()
                args.putSerializable(SOURCE_ARG, product.id)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_sourcesFragment_to_newsFragment, args) }
            }
        }
    }

}