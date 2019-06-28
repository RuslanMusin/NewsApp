package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.DataWrapper
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.util.ApiObserver
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
        sourceAdapter = SourceAdapter(sourceClickCallback);
        binding.sourceList.setAdapter(sourceAdapter);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[SourcesListViewModel::class.java]
        subscribeUi(sourceListViewModel.sources)
    }

    private fun subscribeUi(liveData: LiveData<DataWrapper<Sources>>) {
        liveData.observe(this,
            ApiObserver<Sources>(object: ApiObserver.ChangeListener<Sources> {
                override fun onSuccess(dataWrapper: Sources?) {
                    Log.d("TAG","onSuccess")
                    if (dataWrapper?.sources != null) {
                        hideWaitProgressDialog()
                        dataWrapper.sources?.let { sourceAdapter.setSourceList(it) }
                    } else {
                        showWaitProgressDialog()
                    }
                    binding.executePendingBindings()
                }

                override fun onException(exception: Exception?) {
                    Log.d("TAG","onError")
//                    showErrorDialog(R.string.you_disconnected, true, R.string.disconnected)
                    exception?.message?.let { showErrorDialog(it, true, R.string.disconnected) }
//                    sourceListViewModel.requestSources()
                }

                override fun onDataLoad() {
                    showWaitProgressDialog()
                }

            })
        )
        /*liveData.observe(this,
            Observer { sources ->

            })*/
    }

    override fun onRetry() {
         sourceListViewModel.requestSources()
    }

    interface SourceClickCallback {
        fun onClick(source: Source)
    }

    private val sourceClickCallback = object : SourceClickCallback {
        override fun onClick(source: Source) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                Log.d("TAG", "clicked ${source.name}")
                val action: SourcesFragmentDirections.ActionToNewsFragment = SourcesFragmentDirections.actionToNewsFragment()
                action.sourceId = source.id
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
    }

}