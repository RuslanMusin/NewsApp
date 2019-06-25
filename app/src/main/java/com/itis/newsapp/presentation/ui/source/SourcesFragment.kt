package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import com.itis.newsapp.presentation.base.BaseFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.itis.newsapp.data.network.pojo.response.source.Source

class SourcesFragment : BaseFragment() {

    companion object {

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = com.itis.newsapp.R.layout.data_fragment

    lateinit var mProductAdapter: SourceAdapter

//    lateinit var binding: ListFragmentBinding

    lateinit var binding: com.itis.newsapp.databinding.DataFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false);
        return binding.getRoot();
    }

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
    /*    view?.let {
            (DataBindingUtil.bind(it) as? ListFragmentBinding)?.let {
                binding = it
            }
        }*/
        mProductAdapter = SourceAdapter(mProductClickCallback);
        binding.productsList.setAdapter(mProductAdapter);

        /*btn_enter.setOnClickListener {
            Navigation.findNavController(btn_enter).navigate(com.itis.newsapp.R.id.action_sourcesFragment_to_newsFragment)
        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(SourceListViewModel::class.java)
        subscribeUi(viewModel.sources)
    }

    private fun subscribeUi(liveData: LiveData<List<Source>>) {
        // Update the list when the data changes
        liveData.observe(this,
            Observer<List<Source>> { myProducts ->
                if (myProducts != null) {
                    binding.setIsLoading(false)
                    mProductAdapter.setProductList(myProducts)
                } else {
                    binding.setIsLoading(true)
                }
                // espresso does not know how to wait for data binding's loop so we execute changes
                // sync.
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
//                (activity as MainActivity).show(product)
            }
        }
    }


}