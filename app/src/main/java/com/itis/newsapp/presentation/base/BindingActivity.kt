package com.itis.newsapp.presentation.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.itis.newsapp.presentation.base.navigation.BottomNavOwner

abstract class BindingActivity<T: ViewDataBinding> : BaseActivity(), BottomNavOwner {

    lateinit var binding: T

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, layout)
    }

}