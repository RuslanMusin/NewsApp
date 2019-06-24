package com.itis.newsapp.presentation.ui.news.item

import android.os.Bundle
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_news_item.*

class NewsItemFragment : BaseFragment() {

    companion object {

        fun getInstance() = NewsItemFragment()

    }

    override val layout: Int = R.layout.fragment_news_item

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        arguments?.let {
            tv_arg.text = it.getString("key")
        }
    }


}