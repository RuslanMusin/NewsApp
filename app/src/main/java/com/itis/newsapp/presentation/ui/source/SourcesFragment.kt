package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.databinding.FragmentSourcesBinding
import com.itis.newsapp.presentation.base.fragment.BindingFragment
import com.itis.newsapp.presentation.model.SourceModel
import com.itis.newsapp.presentation.model.common.Response
import com.itis.newsapp.presentation.model.common.Status
import com.itis.newsapp.util.observeOnlyOnce


class SourcesFragment : BindingFragment<FragmentSourcesBinding>() {

    companion object {

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = R.layout.fragment_sources

    lateinit var sourceAdapter: SourceAdapter

    private lateinit var sourceListViewModel: SourcesListViewModel

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        Log.d("TAG","prepare")
        setToolbarData()
        setViewModels()
        observeViewModel()
        bindModel()
        sourceListViewModel.loadSources()
    }

    private fun setToolbarData() {
        setToolbarTitle(R.string.sources)
        setNavigationIconVisibility(false)
    }

    private fun setViewModels() {
        sourceListViewModel = ViewModelProviders.of(
            this, viewModelFactory)[SourcesListViewModel::class.java]
        sourceListViewModel.initState()
    }

    private fun observeViewModel() {
        sourceListViewModel.response.observe(this) { res -> processResponse(res)}
        sourceListViewModel.isDisconnected.observe(this) {
            if(it) {
                showDisconnectView()
            }
        }
    }

    private fun bindModel() {
        binding.indicatorModel = indicatorViewModel
        sourceAdapter = SourceAdapter(sourceClickCallback)
        binding.sourceList.setAdapter(sourceAdapter)
    }

    private fun processResponse(response: Response<List<SourceModel>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data)

            Status.ERROR -> renderErrorState(response.errorMessage)
        }
    }

    private fun renderLoadingState() {
        showWaitProgressDialog()
    }

    private fun renderDataState(sources: List<SourceModel>?) {
        hideWaitProgressDialog()
        sources?.let { sourceAdapter.setSourceList(it) }
    }

    private fun renderErrorState(errorMessage: String?) {
        hideWaitProgressDialog()
        errorMessage?.let { showErrorDialog(it, true, R.string.error) }
    }

    override fun onRetry() {
        sourceListViewModel.response.observeOnlyOnce(this, Observer {
            if (it.status == Status.ERROR) {
                sourceListViewModel.loadSources()
            }
        })
    }

    interface SourceClickCallback {
        fun onClick(source: SourceModel)
    }

    private val sourceClickCallback = object : SourceClickCallback {
        override fun onClick(source: SourceModel) {
            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                val action: SourcesFragmentDirections.ActionToNewsFragment = SourcesFragmentDirections.actionToNewsFragment()
                action.sourceId = source.id
                view?.let { Navigation.findNavController(it).navigate(action) }
            }
        }
    }

}