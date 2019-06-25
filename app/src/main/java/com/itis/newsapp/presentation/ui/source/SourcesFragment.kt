package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.data.network.pojo.response.source.Source
import com.itis.newsapp.databinding.DataFragmentBinding
import com.itis.newsapp.presentation.base.BindingFragment
import kotlinx.android.synthetic.main.data_fragment.*
import kotlinx.android.synthetic.main.fragment_chosen_news.*
import javax.inject.Inject

class SourcesFragment : BindingFragment<DataFragmentBinding>() {

    companion object {

        const val SOURCE_ARG: String = "source_arg"

        fun getInstance() = SourcesFragment()
    }

    override val layout: Int = R.layout.data_fragment

    lateinit var mProductAdapter: SourceAdapter

//    lateinit var binding: DataFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceListViewModel: SourceListViewModel
/*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layout, container, false);
        return binding.getRoot();
    }*/

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        mProductAdapter = SourceAdapter(mProductClickCallback);
        binding.productsList.setAdapter(mProductAdapter);

        /*btn_enter.setOnClickListener {
            Navigation.findNavController(btn_enter).navigate(com.itis.newsapp.R.id.action_sourcesFragment_to_newsFragment)
        }*/
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sourceListViewModel = ViewModelProviders.of(this, viewModelFactory)[SourceListViewModel::class.java]
        subscribeUi(sourceListViewModel.sources)
    }

    private fun subscribeUi(liveData: LiveData<List<Source>>) {
        // Update the list when the data changes
        liveData.observe(this,
            Observer<List<Source>> { myProducts ->
                if (myProducts != null) {
//                    binding.setIsLoading(false)
                    loading_tv.visibility = View.GONE
                    mProductAdapter.setProductList(myProducts)
                } else {
//                    binding.setIsLoading(true)
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
                val args = Bundle()
                args.putSerializable(SOURCE_ARG, product)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_sourcesFragment_to_newsFragment, args) }
//                (activity as MainActivity).show(product)
            }
        }
    }
}