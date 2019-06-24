package com.itis.newsapp.presentation.ui.chosen

import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import androidx.navigation.Navigation
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_chosen_news.*
import javax.inject.Inject

class ChosenNewsFragment : BaseFragment() {

    companion object {

        fun getInstance() = ChosenNewsFragment()

    }

    override val layout: Int = R.layout.fragment_chosen_news

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        btn_enter.setOnClickListener {
            val args = Bundle()
            args.putString("key", "chosenNews")
            Navigation.findNavController(btn_enter).navigate(R.id.action_chosenNewsFragment_to_newsItemFragment, args)
        }
    }

}