package com.itis.newsapp.presentation.ui.source

import android.os.Bundle
import android.provider.Contacts
import androidx.navigation.Navigation
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_chosen_news.*

class SourcesFragment : BaseFragment() {

    companion object {

        fun getInstance() = SourcesFragment()

    }

    override val layout: Int = R.layout.fragment_sources

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        btn_enter.setOnClickListener {
            Navigation.findNavController(btn_enter).navigate(R.id.action_sourcesFragment_to_newsFragment)
        }
    }

}