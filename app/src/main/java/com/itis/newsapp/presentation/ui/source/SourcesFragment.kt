package com.itis.newsapp.presentation.ui.source

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.data.network.callback.ApiObserver
import javax.inject.Inject

class SourcesFragment : BindingFragment<com.itis.newsapp.databinding.FragmentSourcesBinding>() {

    companion object {

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = R.layout.fragment_sources

    lateinit var sourceAdapter: SourceAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: SourcesListViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        bindModel()
    }

    override fun onResume() {
        super.onResume()
        sourceListViewModel.sources.observe(this, observer)
    }

    override fun onPause() {
        super.onPause()
        sourceListViewModel.sources.removeObserver(observer)
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.sources)
        setNavigationIconVisibility(false)
    }

    private fun bindModel() {
        sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[SourcesListViewModel::class.java]
        sourceListViewModel.sources.observe(this, observer)

        sourceAdapter = SourceAdapter(sourceClickCallback)
        binding.sourceList.setAdapter(sourceAdapter)
    }


    private val observer = ApiObserver<Sources>(object :
        ApiObserver.ChangeListener<Sources> {
        override fun onSuccess(dataWrapper: Sources?) {
            if (dataWrapper?.sources != null) {
                hideWaitProgressDialog()
                Log.d("TAG", "setAfterLoading")

                dataWrapper.sources?.let {
                    Log.d("TAG", "setSources")
                    sourceAdapter.setSourceList(it)
                }
            } else {
                showWaitProgressDialog()
            }
            binding.executePendingBindings()
        }

        override fun onException(exception: Exception?) {
            Log.d("TAG", "onError")
            if (exception is NoInternetConnectionException) {
                exception.message?.let { showErrorDialog(it, true, R.string.disconnected) }
                showDisconnectView()
            }
        }

        override fun onDataLoad() {
            showWaitProgressDialog()
        }
    })

    override fun onRetry() {
         showWaitProgressDialog()
         sourceListViewModel.requestSources()
    }

    interface SourceClickCallback {
        fun onClick(source: Source)
    }

    private val sourceClickCallback = object : SourceClickCallback {
        override fun onClick(source: Source) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                val action: SourcesFragmentDirections.ActionToNewsFragment = SourcesFragmentDirections.actionToNewsFragment()
                action.sourceId = source.id
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
    }

}