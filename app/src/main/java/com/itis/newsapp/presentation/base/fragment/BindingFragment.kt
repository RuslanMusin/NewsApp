package com.itis.newsapp.presentation.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.itis.newsapp.presentation.base.navigation.BottomNavOwner

abstract class BindingFragment<T: ViewDataBinding> : BaseFragment(), BottomNavOwner {

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    override fun getCurrentItemId(): Int {
        return (activity as BottomNavOwner).getCurrentItemId()
    }

}