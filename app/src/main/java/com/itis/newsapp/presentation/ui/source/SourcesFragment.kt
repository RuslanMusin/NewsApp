package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.data.network.exception.NoInternetConnectionException
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.data.network.pojo.response.source.Sources
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.data.network.callback.ApiObserver
import com.itis.newsapp.presentation.ui.model.Response
import javax.inject.Inject
import com.itis.newsapp.presentation.ui.model.Status
import com.itis.newsapp.util.observeOnlyOnce
import com.itis.newsapp.R


class SourcesFragment : BindingFragment<com.itis.newsapp.databinding.FragmentSourcesBinding>() {

    companion object {

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = R.layout.fragment_sources

    lateinit var sourceAdapter: SourceAdapter

    private lateinit var sourceListViewModel: SourcesListViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        setToolbarData()
        sourceListViewModel = ViewModelProviders.of(
            this, viewModelFactory)[SourcesListViewModel::class.java]
        observeViewModel()

        binding.indicatorModel = indicatorViewModel
        sourceAdapter = SourceAdapter(sourceClickCallback)
        binding.sourceList.setAdapter(sourceAdapter)
        sourceListViewModel.loadSources()
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.sources)
        setNavigationIconVisibility(false)
    }

    private fun observeViewModel() {
        sourceListViewModel.response.observe(this) { res -> processResponse(res)}
        sourceListViewModel.isDisconnected.observe(this) {
            if(it) {
                showDisconnectView()
            }
        }
    }

    private fun processResponse(response: Response<List<Source>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data)

            Status.ERROR -> renderErrorState(response.errorMessage)
        }
    }

    private fun renderLoadingState() {
        showWaitProgressDialog()
    }

    private fun renderDataState(sources: List<Source>?) {
        Log.d("TAG","update")
        hideWaitProgressDialog()
        sources?.let { sourceAdapter.setSourceList(it) }
    }

    private fun renderErrorState(errorMessage: String?) {
        hideWaitProgressDialog()
        errorMessage?.let { showErrorDialog(it, true, R.string.error) }
    }

    override fun onRetry() {
        Log.d("TAG_source", "onRetry")
        sourceListViewModel.response.observeOnlyOnce(this, Observer {
            Log.d("TAG_source", "status = ${it.status}")
            if (it.status == Status.ERROR) {
                sourceListViewModel.loadSources()
            }
        })
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