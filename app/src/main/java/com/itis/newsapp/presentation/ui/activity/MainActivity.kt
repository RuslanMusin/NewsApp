package com.itis.newsapp.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.itis.newsapp.presentation.base.BaseFragment
import com.itis.newsapp.presentation.base.BindingActivity
import com.itis.newsapp.presentation.base.BindingFragment
import com.itis.newsapp.presentation.base.navigation.BottomNavOwner
import com.itis.newsapp.util.ConnectionLiveData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_indicators.*
import javax.inject.Inject


class MainActivity :
    BindingActivity<com.itis.newsapp.databinding.ActivityMainBinding>(),
    BottomNavOwner {

    override val layout: Int = com.itis.newsapp.R.layout.activity_main

    lateinit var host: NavHostFragment

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        host = supportFragmentManager
            .findFragmentById(com.itis.newsapp.R.id.host) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav_view.setupWithNavController(navController)
    }

    override fun setNavigationIconVisibility(isVisible: Boolean) {
        if(isVisible) {
            toolbar.setNavigationIcon(com.itis.newsapp.R.drawable.ic_arrow_back_white_24dp)
        } else {
            toolbar.setNavigationIcon(null)
        }
    }

    override fun getCurrentItemId(): Int {
        return bottom_nav_view.selectedItemId
    }
}