package com.itis.newsapp.presentation.ui.news.list

import android.os.Bundle
import android.provider.Contacts
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_chosen_news.*

class NewsFragment : BaseFragment() {

    companion object {

        fun getInstance() = NewsFragment()

    }

    override val layout: Int = R.layout.fragment_news

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        btn_enter.setOnClickListener {
            val args = Bundle()
            args.putString("key", "news")
            Navigation.findNavController(btn_enter).navigate(R.id.action_newsFragment_to_newsItemFragment, args)
        }
    }

}