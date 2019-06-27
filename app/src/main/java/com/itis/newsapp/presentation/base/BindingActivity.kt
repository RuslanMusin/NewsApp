package com.itis.newsapp.presentation.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.itis.newsapp.FixAndroidInjection
import com.itis.newsapp.logger.Logger
import dagger.android.AndroidInjector

abstract class BindingActivity<T: ViewDataBinding> : BaseActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        onViewPrepare(savedInstanceState)
    }

}