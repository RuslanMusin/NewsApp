package com.itis.newsapp.presentation.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.newsapp.presentation.base.BindingActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BindingActivity<com.itis.newsapp.databinding.ActivityMainBinding>() {

    override val layout: Int = com.itis.newsapp.R.layout.activity_main

    override fun onViewPrepare(savedInstanceState: Bundle?) {
        super.onViewPrepare(savedInstanceState)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(com.itis.newsapp.R.id.host) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { onBackPressed() }

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(com.itis.newsapp.R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun setNavigationIconVisibility(isVisible: Boolean) {
        if(isVisible) {
            toolbar.setNavigationIcon(com.itis.newsapp.R.drawable.ic_arrow_back_white_24dp)
        } else {
            toolbar.setNavigationIcon(null)
        }
    }

}