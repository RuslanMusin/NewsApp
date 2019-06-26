package com.itis.newsapp.presentation.ui.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.newsapp.R
import com.itis.newsapp.presentation.base.BaseActivity
import com.itis.newsapp.presentation.base.navigation.BackBtnVisibilityListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BackBtnVisibilityListener {

    override val layout: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* var userModel = UserModel()
        userModel.uName = "Androidian"
        userModel.pwd = "123456"
        binding.userModel = userModel*/

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.host) as NavHostFragment? ?: return
        val navController = host.navController
        setupBottomNavMenu(navController)

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun setVisibility(isVisible: Boolean) {
        if(isVisible) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        } else {
            toolbar.setNavigationIcon(null)
        }
    }
}