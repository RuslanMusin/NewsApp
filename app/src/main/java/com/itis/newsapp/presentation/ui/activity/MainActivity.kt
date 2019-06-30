package com.itis.newsapp.presentation.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.itis.newsapp.databinding.ActivityMainBinding
import com.itis.newsapp.presentation.base.activity.BindingActivity
import com.itis.newsapp.presentation.base.navigation.BottomNavOwner
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BindingActivity<ActivityMainBinding>() {

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